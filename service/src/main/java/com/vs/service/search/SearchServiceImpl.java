package com.vs.service.search;

import com.vs.model.enums.Country;
import com.vs.model.enums.Role;
import com.vs.model.user.Cook;
import com.vs.model.user.Customer;
import com.vs.model.user.User;
import com.vs.repository.CookRepository;
import com.vs.repository.UserRepository;
import com.vs.service.geo.GeoSpatial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResults;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    CookRepository cookRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GeoSpatial geoSpatial;


    @Override
    public List findUsersByZIP(String zipCode, Country country, Role role) {
        if (role == Role.COOK) {
            return cookRepository.findAllByBusinessAddress_ZipCodeAndCountry(zipCode, country, role);
        } else {
            return userRepository.findAllByPersonalAddress_ZipCodeAndCountry(zipCode, country, role);
        }
    }

    @Override
    public List<Cook> findUsersNearByLocation(Country country, double[] location, int miles) {
        return geoSpatial.getCooksNearBy(country, location, miles);
    }

    @Override
    public List<Cook> findUsersNearByUserId(Country country, String userId, int miles) {
        User user = userRepository.findOne(userId);
        return geoSpatial.getCooksNearBy(country,user, miles);
    }

    @Override
    public List<Cook> findUsersNearByZipCode(Country country, String zipCode, int miles) {
        return geoSpatial.getCooksNearBy(country, zipCode, miles);
    }


}
