package com.vs.model.email;


import com.vs.model.enums.EmailStatus;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/26/2015.
 */
@Getter
@ToString(exclude="message")
@Document
public class Email {

    @Id
    String _id;

    @NotNull
    @NotBlank
    @org.hibernate.validator.constraints.Email
    private final InternetAddress fromEmail;

    @NotNull
    @NotBlank
    @org.hibernate.validator.constraints.Email
    private final InternetAddress to;

    private List<InternetAddress> toList = new ArrayList<>();

    private final InternetAddress cc;
    private final InternetAddress bcc;

    private List<InternetAddress> ccList = new ArrayList<>();
    private List<InternetAddress> bccList = new ArrayList<>();

    @NotNull
    @NotBlank
    private final String message;

    @NotNull
    @NotBlank
    private final String subject;

    @NotNull
    @NotBlank
    @org.hibernate.validator.constraints.Email
    private final InternetAddress replyTo;
    private final String attachment;

    @Indexed
    private EmailStatus status = EmailStatus.SCHEDULED;

    public void setStatus(EmailStatus status) {
        this.status = status;
    }

    private Email(EmailBuilder builder) {
        this.fromEmail = builder.fromEmail;
        this.to = builder.to;
        this.toList = builder.toList;
        this.cc = builder.cc;
        this.bcc = builder.bcc;
        this.ccList = builder.ccList;
        this.bccList = builder.bccList;
        this.message = builder.message;
        this.subject = builder.subject;
        this.replyTo = builder.replyTo;
        this.attachment = builder.attachment;
    }

    @Data
    public static class EmailBuilder {

        private final InternetAddress fromEmail;
        private final InternetAddress to;
        private List<InternetAddress> toList = new ArrayList<>();
        private InternetAddress cc;
        private InternetAddress bcc;
        private List<InternetAddress> ccList = new ArrayList<>();
        private List<InternetAddress> bccList = new ArrayList<>();
        private final String message;
        private final String subject;
        private InternetAddress replyTo;
        private String attachment;


        public EmailBuilder(String fromEmail, String to, String subject, String message) throws AddressException {
            this.to = new InternetAddress(to);
            this.fromEmail = new InternetAddress(fromEmail);
            this.subject = subject;
            this.message = message;
        }

        public void setReplyTo(String replyTo) throws AddressException {
            this.replyTo = new InternetAddress(replyTo);
        }

        public void setBCC(String... bcc) throws AddressException {
            for (String str : bcc) {
                bccList.add(new InternetAddress(str));
            }
        }

        public void setCC(String... cc) throws AddressException {
            for (String str : cc) {
                ccList.add(new InternetAddress(str));
            }
        }

        public void setTo(String... to) throws AddressException {
            for (String str : to) {
                toList.add(new InternetAddress(str));
            }
        }

        public Email build() {
            return new Email(this);
        }

    }

}
