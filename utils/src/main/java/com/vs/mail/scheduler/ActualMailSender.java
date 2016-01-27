package com.vs.mail.scheduler;

import com.vs.common.constants.EmailConstants;
import com.vs.model.email.Email;
import com.vs.props.ReadYML;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * Created by GeetaKrishna on 1/26/2016.
 */
@Component
@Slf4j
public class ActualMailSender {

    @Autowired
    private ReadYML readYML;

    private JavaMailSenderImpl javaMailSender;
    private MimeMessageHelper helper;
    private MimeMessage message;

    @PostConstruct
    private void init() throws MessagingException {

        javaMailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailProperties.putAll(readYML.getEmail());

        javaMailSender.setJavaMailProperties(mailProperties);
        javaMailSender.setUsername(readYML.getEmail().get(EmailConstants.EMAIL_HOST_NAME));
        log.info("Mail Sender Initialized with host: {} - {}", readYML.getEmail().get(EmailConstants.EMAIL_HOST_NAME), javaMailSender.getHost());

        log.info("Mail Sender initialized");
    }


    protected void sendEmail(Email email) throws MessagingException {
        message = javaMailSender.createMimeMessage();
        helper = new MimeMessageHelper(message, true);

        log.info("Mail Props: {}", javaMailSender.getJavaMailProperties());

        String from="";
        // FROM
        if(email.getFromEmail()!=null && !email.getFromEmail().toString().isEmpty()) {
            from = email.getFromEmail().getAddress();

        } else {
            from = "<"+readYML.getEmail().get(EmailConstants.EMAIL_FROM)+">"+email.getFromEmail();
        }

        if(!from.isEmpty()) {
            helper.setFrom(from);
        } else {
            log.error("Invalid From Address");
            return;
        }

        Boolean isToFound = Boolean.FALSE;
        // TO
        if(!email.getToList().isEmpty()) {
            for (InternetAddress address: email.getToList()) {
                helper.setCc(address);
                isToFound = Boolean.TRUE;
            }
        } else {
            helper.setTo(email.getTo());
            isToFound = Boolean.TRUE;
        }

        if(!isToFound) {
            log.error("To Not Found");
            return;
        }

        // ATTACHMENT
        if(email.getAttachment()!=null) {
            helper.addAttachment(email.getAttachment(), new File(email.getAttachment()));
        }


        // MESSAGE TEXT: Use the true flag to indicate the text included is HTML
        if(!email.getMessage().isEmpty()) {
            helper.setText(email.getMessage(), Boolean.TRUE);
        } else {
            log.error("Email Content to send not Found");
            return;
        }

        // SEND THE MESSAGE
        javaMailSender.send(message);
    }
    }
