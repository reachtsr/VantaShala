package com.vs.auth.services;

import com.vs.auth.repository.AUserRepository;
import com.vs.auth.repository.UserSocialConnectionRepository;
import com.vs.model.user.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by GeetaKrishna on 1/9/2016.
 */

@Service
@Data
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService{

    @Autowired
    private AUserRepository userService;

    @Autowired
    UserSocialConnectionRepository userSocialConnectionRepository;

    @Override
    public final User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("Not Exist User");
        }

        return user;
    }
}
