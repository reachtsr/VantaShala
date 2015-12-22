package com.vs.service.user;

import com.vs.model.user.User;

/**
 * Created by GeetaKrishna on 12/21/2015.
 */


public interface IUserService {
    public void saveCook(User user);
    public void saveCustomer(User user);

    public User getCook(String firstName);

    public User getCookByKitchenName(String kitchenName);
}
