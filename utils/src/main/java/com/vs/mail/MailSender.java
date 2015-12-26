package com.vs.mail;

import com.vs.common.AppConstants;
import com.vs.props.ReadYML;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
@Component
@Slf4j
public class MailSender {

    @Autowired
    ReadYML readYML;

    public MailSender() {
        log.info("MailSender Initialized");
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Properties mailProperties = new Properties();
        mailProperties.putAll(readYML.getEmail());

        mailSender.setJavaMailProperties(mailProperties);
        log.info("Mail Sender Initialized with host: {}", readYML.getEmail().get(AppConstants.EMAIL_HOST_NAME));
        return mailSender;
    }

}
