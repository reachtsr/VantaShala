package com.vs.mail;

import com.vs.common.constants.EmailConstants;
import com.vs.model.email.Email;
import com.vs.props.ReadYML;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by GeetaKrishna on 12/26/2015.
 */
@Component
@Slf4j
public class SendEmail {


    @Autowired
    ReadYML readYML;

    MimeMessage message;
    MimeMessageHelper helper;
    JavaMailSender javaMailSender;

    @Autowired
    EmailScheduler emailScheduler;

    @Autowired
    MailSender mailSender;


    @PostConstruct
    public void init() throws MessagingException {

        javaMailSender = mailSender.javaMailSender();
        MimeMessage message = mailSender.javaMailSender().createMimeMessage();

        helper = new MimeMessageHelper(message, true);
        helper.setFrom(readYML.getEmail().get(EmailConstants.EMAIL_FROM));
        log.info("Send Email initialized");
    }

    public void sendEmail(Email email) throws MessagingException{
        helper.setTo(email.getTo());
        if(email.getAttachment()!=null) {
            helper.addAttachment(email.getAttachment(), new File(email.getAttachment()));
        }
        // use the true flag to indicate the text included is HTML
        helper.setText(email.getMessage(), Boolean.TRUE);
        helper.setFrom("<"+readYML.getEmail().get(EmailConstants.EMAIL_FROM)+">"+email.getFromEmail());
        email.setJavaXMessage(message);
        emailScheduler.scheduleEmailToSend(email);

        //mailSender.javaMailSender().send(message);
    }
}
