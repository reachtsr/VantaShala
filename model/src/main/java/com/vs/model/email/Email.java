package com.vs.model.email;


import com.vs.model.enums.EmailStatus;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
@Setter
@ToString(exclude = "message")
@Document
public class Email {

    @Id
    String _id;

    @NotNull
    @NotBlank
    @org.hibernate.validator.constraints.Email
    private InternetAddress fromEmail;

    @NotNull
    @NotBlank
    @org.hibernate.validator.constraints.Email
    private InternetAddress to;

    private List<InternetAddress> toList = new ArrayList<>();
    private List<InternetAddress> ccList = new ArrayList<>();
    private List<InternetAddress> bccList = new ArrayList<>();

    private InternetAddress cc;
    private InternetAddress bcc;

    @NotNull
    @NotBlank
    private String message;

    @NotNull
    @NotBlank
    private String subject;

    @NotNull
    @NotBlank
    @org.hibernate.validator.constraints.Email
    private InternetAddress replyTo;
    private String attachment;

    @Indexed
    private EmailStatus status = EmailStatus.SCHEDULED;

    public void setStatus(EmailStatus status) {
        this.status = status;
    }

    public Email() {
    }

}
