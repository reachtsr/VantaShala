package com.vs.mail;

import com.vs.model.email.Email;
import com.vs.model.enums.EmailStatus;
import com.vs.repository.DBOperations;
import com.vs.repository.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/26/2015.
 */
@Component
@Slf4j
public class EmailScheduler {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    MailSender mailSender;

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    DBOperations dbOperations;


    List<Email> mailsToSend = new ArrayList<>();
    List<Email> mailsSent = new ArrayList<>();

    public void scheduleEmailToSend(Email email){

        // First save to DB and cache them.
        email.setStatus(EmailStatus.SCHEDULED);
        Email nEmail = emailRepository.save(email);
        mailsToSend.add(nEmail);

        mailsSent.add(nEmail);
    }


    // Todo move value to application yaml. @Scheduled(cron = "${cron.expression}")
    @Scheduled(fixedDelay = 10000)
    public synchronized void sendEmail() {
        log.debug("Scanning emails to Send : {}",  dateFormat.format(new Date()));
        Iterator<Email> i = mailsToSend.iterator();
        while (i.hasNext()) {
            Email email = i.next();
            i.remove();
            log.info("Sending email: {}", email);
            mailSender.javaMailSender().send(email.getJavaXMessage());
            email.setStatus(EmailStatus.SENT);
            mailsSent.add(email);
        }
    }

    // Todo move value to application yaml. @Scheduled(cron = "${cron.expression}")
    // Todo update only required fiiled instead of entire document as below
    // updateEmailStatus.updateStatus, already in place
    @Scheduled(fixedDelay = 30000)
    public synchronized void updateDB() {
        log.debug("Running Update EmailSent Status: {}",  dateFormat.format(new Date()));
        Iterator<Email> i = mailsToSend.iterator();
        while (i.hasNext()) {
            Email email = i.next();
            i.remove();
            dbOperations.updateEmailStatus(email);
            log.info("Email Sent Status Update: {}", email);
        }
    }

}
