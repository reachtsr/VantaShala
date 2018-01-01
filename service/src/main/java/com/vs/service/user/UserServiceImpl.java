package com.vs.service.user;

import com.google.common.base.Preconditions;
import com.google.maps.model.LatLng;
import com.vs.common.filters.AppConstants;
import com.vs.model.AddNewFiledsToCollection;
import com.vs.model.SaveFileModel;
import com.vs.model.enums.Role;
import com.vs.model.enums.UserStatusEnum;
import com.vs.model.user.Cook;
import com.vs.model.user.CustomerCookSubscription;
import com.vs.model.user.User;
import com.vs.repository.CookRepository;
import com.vs.repository.DBOperations;
import com.vs.repository.SubscriptionRepository;
import com.vs.repository.UserRepository;
import com.vs.service.SaveFile;
import com.vs.service.geo.GoogleLonLatService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;

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

//    @Autowired
//    GeoSpatial geoSpatial;

    @Autowired
    GoogleLonLatService googleLonLatService;

    private double[] convertGoogleLatLngToDouble(LatLng latLng) {
        double[] point = { latLng.lat, latLng.lng};
        return point;
    }


    private Point convertGoogleLatLngToPoint(LatLng latLng) {
        return new Point(latLng.lat, latLng.lng);
    }

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

            LatLng latLng = googleLonLatService.getLatLon(cook.getBusinessAddress());
            if (!Objects.isNull(latLng)) {
                user.setLoc(new Point(latLng.lng, latLng.lat));
            }
            List<Cook> existingCooks = cookRepository.findByKitchenName(kitchenName, Role.COOK);
            if (existingCooks.size() == 0) {
                userRepository.insert(user);
            } else {
                throw new Exception("DUPLICATE KITCHEN NAME NOT ALLOWED: " + ((Cook) user).getKitchenName());
            }
        } else {
            LatLng latLng = googleLonLatService.getLatLon(user.getPersonalAddress());
            if (!Objects.isNull(latLng)) {
                user.setLoc(new Point(latLng.lng, latLng.lng));
            }
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

        storeSubscribersToDB(cookId, customerId);
        storeSubscribersToDB(customerId, cookId);
        return Boolean.TRUE;
    }

    private void storeSubscribersToDB(String requestToSubscribe, String requestFrom) {
        User user = userRepository.findOne(requestToSubscribe);
        Preconditions.checkNotNull(user);
        CustomerCookSubscription customerCookSubscription = subscriptionRepository.findByUserId(requestFrom);
        if (customerCookSubscription != null) {
            if (!customerCookSubscription.getSubscribers().contains(requestToSubscribe)) {
                customerCookSubscription.getSubscribers().add(requestToSubscribe);
                subscriptionRepository.save(customerCookSubscription);
            }
        } else {
            customerCookSubscription = new CustomerCookSubscription();
            ArrayList<String> list = new ArrayList<>();
            list.add(requestToSubscribe);
            customerCookSubscription.setUserId(requestFrom);
            customerCookSubscription.setSubscribers(list);
            subscriptionRepository.insert(customerCookSubscription);
        }
    }

    @Override
    public List getSubscriptions(String userId, Role role) {

        Preconditions.checkNotNull(userId);
        List<User> list = new ArrayList<>();

        CustomerCookSubscription customerCookSubscription = subscriptionRepository.findByUserId(userId);
        customerCookSubscription.getSubscribers().forEach(u -> list.add(userRepository.findOne(u)));

        return list;
    }

}
