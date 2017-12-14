package com.vs.service.search;

import com.vs.model.enums.Country;
import com.vs.model.enums.Role;
import com.vs.model.user.Cook;
import com.vs.model.user.User;
import org.springframework.data.geo.GeoResults;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public interface ISearchService {

    public List<Cook> findUsersNearByLocation(Country country, double[] locations, int miles);

    public List<Cook> findUsersNearByZipCode(Country country, String zipCode, int miles);

    public List<Cook> findUsersNearByUserId(Country country, String userId, int miles);

    public List findUsersByZIP(String zipCode, Country country, Role role);
}
