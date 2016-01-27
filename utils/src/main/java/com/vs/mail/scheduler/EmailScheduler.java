package com.vs.mail.scheduler;

import com.vs.model.email.Email;
import com.vs.model.enums.EmailStatus;
import com.vs.repository.DBOperations;
import com.vs.repository.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by GeetaKrishna on 12/26/2015.
 */
@Component
@Slf4j
public class EmailScheduler {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private ActualMailSender mailSender;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private DBOperations dbOperations;

    private Queue<Email> mailsToSend = new ConcurrentLinkedQueue<>();
    private Queue<Email> mailsSent = new ConcurrentLinkedQueue<>();

    public void scheduleEmailToSend(Email _email){
        // First save to DB and cache them.
        Email email = emailRepository.save(_email);
        mailsToSend.add(email);
    }


    // Todo move value to application yaml. @Scheduled(cron = "${cron.expression}")
    @Scheduled(fixedDelay = 10000)
    private synchronized void sendEmail() {
        log.debug("Scanning emails to Send : {}",  dateFormat.format(new Date()));
        for (Email email; (email = mailsToSend.poll()) != null;){
            try {
                log.info("Sending email: {}", email);
                mailSender.sendEmail(email);
                email.setStatus(EmailStatus.SENT);
                mailsSent.add(email);
            }catch (Exception e) {
                log.error(" Error while sending Email: {}", e);
            }
        }
    }

    // Todo move value to application yaml. @Scheduled(cron = "${cron.expression}")
    // Todo update only required fiiled instead of entire document as below
    // updateEmailStatus.updateStatus, already in place
    @Scheduled(fixedDelay = 30000)
    private synchronized void updateDB() {
        log.debug("Running Update EmailSent Status: {}",  dateFormat.format(new Date()));

        for (Email email; (email = mailsSent.poll()) != null;){
            dbOperations.updateEmailStatus(email);
            log.info("Email Sent Status Update: {}", email);
        }

    }

    // @TODO Make it every 24 hours. Midnight @ PST
    @Scheduled(fixedDelay = 30000)
    private synchronized void archiveEmailEntries() {
        log.debug("Archiving Send emails to another document {}",  dateFormat.format(new Date()));

        //@TODO Complete this. Should be written on mongo side.
    }

}
