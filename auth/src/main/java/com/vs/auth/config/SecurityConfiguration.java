package com.vs.auth.config;

import com.vs.auth.repository.AUserRepository;
import com.vs.auth.services.AuthSuccessHandler;
import com.vs.auth.services.SimpleSocialUsersDetailService;
import com.vs.auth.services.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.UserIdSource;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialAuthenticationProvider;
import org.springframework.social.security.SocialAuthenticationServiceLocator;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.inject.Inject;



/**
 * Created by GeetaKrishna on 1/9/2016.
 */
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private ApplicationContext context;


    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    AUserRepository userRepository;

    @Inject
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    AuthSuccessHandler authSuccessHandler;

    @Inject
    private SocialAuthenticationServiceLocator socialAuthenticationServiceLocator;

    @Inject
    private UserIdSource userIdSource;


    @Autowired
    public void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(socialAuthenticationProvider());
      //  auth.authenticationEventPublisher();

    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder builder) throws Exception{
//        builder
//                .authenticationProvider(socialAuthenticationProvider());
//               // .userDetailsService(userDetailsService());
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                //Spring Security ignores request to static resources such as CSS or JS files.
                .ignoring()
                .antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


//        http
//                .csrf().disable()
//                .formLogin()
//                .loginPage("/login")
//               // .defaultSuccessUrl("/", true)
//                .loginProcessingUrl("/login/authenticate")
//                .failureHandler(new CustomLoginFailureHandler())
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .deleteCookies("JSESSIONID")
//                .and()
//                .authorizeRequests()
//                .antMatchers("/admin/**", "/favicon.ico", "/resources/**", "/auth/**", "/signin/**", "/signup/**", "/disconnect/facebook").permitAll()
//                .antMatchers("/**").authenticated()
//                .and()
//                .rememberMe()
//                .and()
//                .apply(
//                        new SpringSocialConfigurer());


        http
                .csrf().disable()
                .anonymous().and()
                .formLogin()
               // .successHandler(new AuthSuccess())
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .loginProcessingUrl("/login/authenticate")
                .failureHandler(new CustomLoginFailureHandler())
                .and()
                .logout()
                .deleteCookies("SESSION")
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/auth/**",
                        "/login",
                        "/error",
                        "/css/**",
                        "/rest/**",
                        "/assets/**",
                        "/signup",
                        "/index.html",
                        "/video-background.html"
                ).permitAll()
                .antMatchers("/**").
                authenticated()
               // hasRole("DEFAULT")
                .and()
                .apply(new SpringSocialConfigurer());

    }

    @Bean
    public SocialAuthenticationProvider socialAuthenticationProvider(){
       // System.out.println("==> "+socialAuthenticationToken);
        return new SocialAuthenticationProvider(usersConnectionRepository, socialUsersDetailService());
    }

    @Bean
    public SocialUserDetailsService socialUsersDetailService() {
        return new SimpleSocialUsersDetailService(userDetailsService);
    }

//    @Bean
//    public UserIdSource userIdSource() {
//        return new AuthenticationNameUserIdSource();
//    }

//    @Bean
//    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
//        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public TextEncryptor textEncryptor() {
        return Encryptors.noOpText();
    }
}