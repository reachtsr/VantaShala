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

    public void scheduleEmailToSend(Email _email) {
        // First save to DB and cache them.
        Email email = emailRepository.save(_email);
        mailsToSend.add(email);
    }

    @Scheduled(fixedDelayString = "${vs.email.timer.scanTimer}")
    private synchronized void sendEmail() {
        log.debug("Scanning emails to Send : {}", dateFormat.format(new Date()));
        for (Email email; (email = mailsToSend.poll()) != null; ) {
            try {
                log.info("Sending Email To: {}", email.getTo());
                mailSender.sendEmail(email);
                email.setStatus(EmailStatus.SENT);
                dbOperations.updateEmailStatus(email);
                log.info("Updating Email Sent Status in DB: {},  Status: {}", email.get_id(), email.getStatus());
            } catch (Exception e) {
                log.error(" Error while sending Email: {}", e);

                email.setStatus(EmailStatus.FAILED);
                dbOperations.updateEmailStatus(email);
                log.info("Updating Email Failed Status in DB: {},  Status: {}", email.get_id(), email.getStatus());
            }
        }
    }

    @Scheduled(fixedDelayString = "${vs.email.timer.dbTable}")
    private synchronized void scanDatabase() {
        log.debug("Scanning emails to Send in DB: {}", dateFormat.format(new Date()));
        List<Email> scheduled = emailRepository.findByStatus(EmailStatus.SCHEDULED.name());
        List<Email> failed = emailRepository.findByStatus(EmailStatus.FAILED.name());
        scheduled.forEach(e -> mailsToSend.add(e));
        failed.forEach(e -> mailsToSend.add(e));
    }

    @Scheduled(cron = "${vs.email.timer.dbArchive}")
    private synchronized void archiveEmailEntries() {
        log.debug("Archiving Send emails to another document {}", dateFormat.format(new Date()));

        //@TODO Complete this. Should be written on mongo side. Write a Function or procedure on mongo db to move the sent items from
        // email document to emailArchive document.
    }

}
