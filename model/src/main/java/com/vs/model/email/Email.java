package com.vs.model.email;

import com.vs.model.enums.EmailStatus;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by GeetaKrishna on 12/26/2015.
 */
@Data
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class Email {


    @Id
    String _id;

    @NotNull
    @NotBlank
    private InternetAddress fromEmail;

    @NotNull
    @NotBlank
    private InternetAddress to;

    private List<InternetAddress> toList = new ArrayList<>();

    private String message;
    private String subject;

    private String attachment;

    public void setTo(String to) throws AddressException{
        this.to = new InternetAddress(to);
    }

    public void setTo(String... to) throws AddressException{
        for (String str: to ) {
            toList.add(new InternetAddress(str));
        }
    }

    private EmailStatus status;

    @Transient
    private MimeMessage javaXMessage;
}
