package com.vs.auth.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by GeetaKrishna on 2/13/2016.
 */
@Controller
@Slf4j
public class MainController {

    @Autowired
    private SocialControllerUtil util;


    @PostConstruct
    public void init(){
        log.info(":: Main Controller Loaded ::");

    }

//    @RequestMapping("/error")
//    public String error(HttpServletRequest request, Model model) {
//        model.addAttribute("errorCode", request.getAttribute("javax.servlet.error.status_code"));
//        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
//        String errorMessage = null;
//        if (throwable != null) {
//            errorMessage = throwable.getMessage();
//        }
//        model.addAttribute("errorMessage", errorMessage);
//        return "error";
//    }

    @RequestMapping("/")
    public String home(HttpServletRequest request, Principal currentUser, Model model) {
        util.setModel(request, currentUser, model);

        return "home";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Principal currentUser, Model model) {
         util.setModel(request, currentUser, model);
        return "login";
    }

}
