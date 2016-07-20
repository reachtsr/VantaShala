package com.vs.auth.config;

import com.vs.auth.repository.MongoUsersConnectionRepository;
import com.vs.auth.repository.AUserRepository;
import com.vs.auth.repository.UserSocialConnectionRepository;
import com.vs.auth.services.AccountConnectionSignUpService;
import com.vs.auth.services.UserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.google.connect.GoogleConnectionFactory;
import org.springframework.social.security.SocialAuthenticationServiceLocator;

import javax.inject.Inject;


/**
 * Created by GeetaKrishna on 1/9/2016.
 */
@Slf4j
@Configuration
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private AUserRepository userRepository;

    @Inject
    private UserSocialConnectionRepository userSocialConnectionRepository;

    public SocialConfig() {
        log.info("CREATING BEAN ----->");
    }

    @Bean
    public ConnectionSignUp autoConnectionSignUp() {
        return new AccountConnectionSignUpService(userRepository);
    }

    @Override
    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
        MongoUsersConnectionRepository repository = new MongoUsersConnectionRepository(
                userSocialConnectionRepository, (SocialAuthenticationServiceLocator)connectionFactoryLocator, Encryptors.noOpText());
        repository.setConnectionSignUp(autoConnectionSignUp());
        return repository;
    }

    @Override
    public void addConnectionFactories(ConnectionFactoryConfigurer connectionFactoryConfigurer, Environment environment) {

        connectionFactoryConfigurer.addConnectionFactory(new GoogleConnectionFactory(
                environment.getProperty("spring.social.google.app-id"),
                environment.getProperty("spring.social.google.app-secret")));



    }

//    @Bean
//    public ConnectController connectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
//        ConnectController connectController = new ConnectController(connectionFactoryLocator, connectionRepository);
//        connectController.addInterceptor(new FBIntercepptor());
//        return connectController;
//    }


    @Override
    public UserIdSource getUserIdSource() {
        return new UserIdSource() {
            @Override
            public String getUserId() {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null) {
                    throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
                }
                return authentication.getName();
            }
        };
    }

//    @Bean
//    public ProviderSignInController providerSignInController(ConnectionFactoryLocator connectionFactoryLocator, UsersConnectionRepository usersConnectionRepository) {
//        return new ProviderSignInController(connectionFactoryLocator, usersConnectionRepository, new SimpleSignInAdapter(new HttpSessionRequestCache()));
//    }

//    @Bean
//    public ReconnectFilter apiExceptionHandler(UsersConnectionRepository usersConnectionRepository, UserIdSource userIdSource) {
//        return new ReconnectFilter(usersConnectionRepository, userIdSource);
//    }
}
