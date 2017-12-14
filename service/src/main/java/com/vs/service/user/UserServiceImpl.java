package com.vs.service.user;

import com.google.common.base.Preconditions;
import com.vs.common.filters.AppConstants;
import com.vs.model.AddNewFiledsToCollection;
import com.vs.model.SaveFileModel;
import com.vs.model.enums.Role;
import com.vs.model.enums.UserStatusEnum;
import com.vs.model.geo.ZipData;
import com.vs.model.user.Cook;
import com.vs.model.user.CustomerToCookSubscription;
import com.vs.model.user.User;
import com.vs.repository.CookRepository;
import com.vs.repository.DBOperations;
import com.vs.repository.SubscriptionRepository;
import com.vs.repository.UserRepository;
import com.vs.service.SaveFile;
import com.vs.service.geo.GeoSpatial;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
@Slf4j
@Data
public abstract class UserServiceImpl implements IUserService {

    private Role role = null;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CookRepository cookRepository;

    @Autowired
    private SaveFile saveFile;

    @Autowired
    DBOperations dbOperations;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    GeoSpatial geoSpatial;

    public UserServiceImpl(Role role) throws Exception {
        this.role = role;
        if (this.role == null) {
            throw new Exception("UserService - Role is NULL ");
        }
        log.info("Role Assigned: {}", role.name());
    }

    @Override
    public void createUser(User user) throws Exception {

        if (user instanceof Cook) {
            Cook cook = (Cook) user;
            String kitchenName = cook.getKitchenName();
            ZipData zipData = geoSpatial.getCoOrdinates(cook.getBusinessAddress().getZipCode());
            if (!Objects.isNull(zipData)) {
                user.setLocation(zipData.getLoc());
            }
            List<Cook> existingCooks = cookRepository.findByKitchenName(kitchenName, Role.COOK);
            if (existingCooks.size() == 0) {
                userRepository.insert(user);
            } else {
                throw new Exception("DUPLICATE KITCHEN NAME NOT ALLOWED");
            }
        } else {
            ZipData zipData = geoSpatial.getCoOrdinates(user.getPersonalAddress().getZipCode());
            user.setLocation(zipData.getLoc());
            userRepository.insert(user);
        }
    }


    @Override
    public String saveFile(String userName, SaveFileModel saveFileModel) {
        String path = saveFile.saveFile(userName, saveFileModel);

        Map<String, String> map = new HashMap();
        map.put(AppConstants.PROFILE_PICTURE, path);

        AddNewFiledsToCollection addNewFiledsToCollection = new AddNewFiledsToCollection();
        addNewFiledsToCollection.setId(userName);
        addNewFiledsToCollection.setCollectionType(User.class.getSimpleName().toLowerCase());
        addNewFiledsToCollection.setKeyValues(map);

        dbOperations.addFieldsToCollection(addNewFiledsToCollection);

        return path;
    }


    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Cook getUserByKitchenName(String name) {
        List<Cook> list = cookRepository.findByKitchenName(name, Role.COOK);
        return list.get(0);
    }

    @Override
    public List<User> getCookByFirstName(String name) {
        return userRepository.findByFirstName(name, Role.COOK);
    }

    @Override
    public List<User> getCustomerByFirstName(String name) {
        return userRepository.findByFirstName(name, Role.CUSTOMER);
    }

    @Override
    public User getUserByUserName(String name) {
        return userRepository.findOne(name);
    }

    @Override
    public void enableOrDisableUser(String userName, UserStatusEnum userStatus) throws Exception {
        User user = userRepository.findOne(userName);
        Preconditions.checkNotNull(user, "User Not Found: " + userName);
        user.setStatus(userStatus);
        userRepository.save(user);
    }

    @Override
    public long getUserCount() {
        return userRepository.count();
    }

    @Override
    public long getUserCount(Role role) {
        return userRepository.countByRole(role);
    }

    @Override
    public List<User> listUsers(Role role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findUser(String searchString, Role role) {
        return userRepository.findByLastNameOrFirstNameOrUserNameAndRole(searchString, searchString, searchString, role);
    }

    @Override
    public List<User> findUser(String searchString) {
        return userRepository.findByLastNameOrFirstNameOrUserName(searchString, searchString, searchString);
    }

    @Override
    public boolean subscribeCustomerToCook(String cookId, String customerId) {
        User user = userRepository.findOne(cookId);
        Preconditions.checkNotNull(user);
        CustomerToCookSubscription customerToCookSubscription = subscriptionRepository.findByCook(cookId);
        if (customerToCookSubscription != null) {
            if (!customerToCookSubscription.getCustomers().contains(customerId)) {
                customerToCookSubscription.getCustomers().add(customerId);
                subscriptionRepository.save(customerToCookSubscription);
            }
        } else {
            customerToCookSubscription = new CustomerToCookSubscription();
            ArrayList<String> list = new ArrayList<>();
            list.add(customerId);
            customerToCookSubscription.setCook(cookId);
            customerToCookSubscription.setCustomers(list);
            subscriptionRepository.insert(customerToCookSubscription);
        }
        return Boolean.TRUE;
    }

}
