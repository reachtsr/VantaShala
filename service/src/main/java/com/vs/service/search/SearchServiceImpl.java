package com.vs.service.search;

import com.vs.model.user.User;
import com.vs.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public class SearchServiceImpl implements ISearchService {

    @Autowired
    SearchRepository searchRepository;

    @Override
    public List<User> findByBusinessAddressZipCode(String zipCode) {
        return searchRepository.findByBusinessAddressZipCode(zipCode);
    }


    @Override
    public List<User> findByBusinessAddressZipCodeRadius(String zipCode) throws NumberFormatException{
        long iZipCode = Long.valueOf(zipCode);
        long max = iZipCode + 3;
        long min = iZipCode - 3;
        return searchRepository.findByBusinessAddressZipCodeBetween(min, max);
    }


}
