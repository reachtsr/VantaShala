package com.vs.service.search;

import com.vs.model.user.Cook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
@Service
public class SearchServiceImpl implements ISearchService {


    @Override
    public List<Cook> findCooksByZIP(String zipCode) {
        return null;
    }

    @Override
    public List<Cook> findCooksByCurrentLocation(String zipCode) {
        return null;
    }

    @Override
    public List<Cook> findCustomersByZIP(String zipCode) {
        return null;
    }

    @Override
    public List<Cook> findCustomersByCurrentLocation(String zipCode) {
        return null;
    }
}
