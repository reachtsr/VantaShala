package com.vs.mail;

import com.vs.mail.scheduler.EmailScheduler;
import com.vs.model.email.Email;
import com.vs.model.email.EmailGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by GeetaKrishna on 12/26/2015.
 */
@Component
@Slf4j
public class ProcessEmail {

    @Autowired
    EmailScheduler emailScheduler;

    public void sendEmail(EmailGenerator emailGenerator) throws MessagingException, IllegalAccessException, InvocationTargetException {
        Email email = new Email();

        BeanUtils.copyProperties(email, emailGenerator);

        log.info("Email Scheduled to send: {}", email);
        if (email.getMessage().isEmpty()) {
            log.error("Message Body is empty");
            return;
        }
        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email.getFromEmail().getAddress())) {
            log.error("From is not valid");
            return;
        }
        if (email.getMessage().isEmpty()) {
            log.error("Message Body is empty");
            return;
        }
        emailScheduler.scheduleEmailToSend(email);
    }
}
