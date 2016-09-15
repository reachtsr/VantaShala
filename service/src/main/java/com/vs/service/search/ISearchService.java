package com.vs.service.search;

import com.vs.model.user.Cook;
import com.vs.model.user.User;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public interface ISearchService {

    public List<Cook> findCooksByZIP(String zipCode);
    public List<Cook> findCooksByCurrentLocation(String zipCode);
    public List<Cook> findCustomersByZIP(String zipCode);
    public List<Cook> findCustomersByCurrentLocation(String zipCode);
}
