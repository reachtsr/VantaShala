package com.vs.mail;

import com.vs.common.constants.EmailConstants;
import com.vs.mail.scheduler.EmailScheduler;
import com.vs.model.email.Email;
import com.vs.props.ReadYML;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;

/**
 * Created by GeetaKrishna on 12/26/2015.
 */
@Component
@Slf4j
public class ProcessEmail {

    @Autowired
    EmailScheduler emailScheduler;

    public void sendEmail(Email email) throws MessagingException{
        emailScheduler.scheduleEmailToSend(email);
    }
}
