package com.vs.mail;

import com.vs.mail.scheduler.EmailScheduler;
import com.vs.model.email.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;

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
