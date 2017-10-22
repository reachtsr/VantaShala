package com.vs.mail.scheduler;

import com.vs.model.email.Email;
import com.vs.model.props.EmailProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Objects;
import java.util.Properties;

/**
 * Created by GeetaKrishna on 1/26/2016.
 */
@Component
@Slf4j
public class ActualMailSender {

    @Autowired
    private EmailProperties emailProperties;

    private JavaMailSenderImpl javaMailSender;

    @Value("${vs.email.send}")
    private boolean sendMail;

    @PostConstruct
    private void init() throws MessagingException {

        javaMailSender = new JavaMailSenderImpl();
        Properties mailProperties = new Properties();
        mailProperties.putAll(emailProperties.getEmail());

        javaMailSender.setJavaMailProperties(mailProperties);
        javaMailSender.setPassword(emailProperties.getPassword());

        log.info("Mail Sender Initialized with host: {}", javaMailSender.getHost());
    }

    private boolean isOutgoingEmailPresent(Email email, MimeMessageHelper helper) throws MessagingException {

        boolean isEmailPresent = false;

        if (Objects.nonNull(email.getTo())) {
            helper.setTo(email.getTo());
            isEmailPresent = true;
        }
        if (Objects.nonNull(email.getCc())) {
            helper.setCc(email.getCc());
            isEmailPresent = true;
        }
        if (Objects.nonNull(email.getBcc())) {
            helper.setBcc(email.getBcc());
            isEmailPresent = true;
        }
        if (!email.getToList().isEmpty()) {
            email.getToList().add(email.getFromEmail());
            helper.setTo(email.getToList().toArray(new InternetAddress[0]));
            isEmailPresent = true;
        }
        if (!email.getBccList().isEmpty()) {
            helper.setBcc(email.getBccList().toArray(new InternetAddress[0]));
            isEmailPresent = true;
        }
        if (!email.getCcList().isEmpty()) {
            helper.setCc(email.getCcList().toArray(new InternetAddress[0]));
            isEmailPresent = true;
        }

        return isEmailPresent;
    }

    protected void sendEmail(Email email) throws MessagingException {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            log.info("Mail Props: {}", javaMailSender.getJavaMailProperties());

            String from;
            // FROM
            from = email.getFromEmail().getAddress();
            helper.setFrom(from, "VantaShala");

            if (!isOutgoingEmailPresent(email, helper)) {
                log.info("No Outgoing email found");
                return;
            }

            // ATTACHMENT
            if (!Objects.isNull(email.getAttachment())) {
                helper.addAttachment(email.getAttachment(), new File(email.getAttachment()));
            }

            // MESSAGE TEXT: Use the true flag to indicate the text included is HTML
            if (!Objects.isNull(email.getMessage())) {
                helper.setText(email.getMessage(), Boolean.TRUE);
            } else {
                log.error("Email Content to send not Found");
                return;
            }

            // SUBJECT
            if (email.getSubject() != null && !email.getSubject().isEmpty()) {
                helper.setSubject(email.getSubject());
            } else {
                log.error("Email Subject not Found");
                return;
            }

            // REPLYTO
            if (email.getReplyTo() != null && !email.getReplyTo().getAddress().isEmpty()) {
                helper.setReplyTo(email.getReplyTo());
            } else {
                helper.setReplyTo(emailProperties.getReplyToEmail());
            }

            // SEND THE MESSAGE
            if (sendMail) {
                log.info("Sending email: {}", email.getTo());
                javaMailSender.send(message);
            } else {
                log.info("No email sent. Configure as needed.");
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
