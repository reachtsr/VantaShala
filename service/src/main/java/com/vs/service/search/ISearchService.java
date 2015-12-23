package com.vs.service.search;

import com.vs.model.user.User;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public interface ISearchService {

    public List<User> findByBusinessAddressZipCode(String zipCode);
    public List<User> findByBusinessAddressZipCodeRadius(String zipCode);
}
