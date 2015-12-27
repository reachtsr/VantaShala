package com.vs.service.email;

import com.vs.common.constants.EmailConstants;
import com.vs.common.constants.VMConstants;
import com.vs.model.email.Email;
import com.vs.model.menu.Menu;
import com.vs.model.order.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * Created by GeetaKrishna on 12/26/2015.
 */

@Slf4j
@Service
public class EmailService extends CommonEmailService {

    // ToDo Create VM Templates and update them accordingly.
    // ToDo Move String Constants to application yaml file and read them as needed.
    // ToDo Move haigopi@gmail.com to application yaml.
    public void sendOrderCreateEmail(Order order) {
        String to = userRepository.findOne(order.getOrderedBy()).getEmail();

        Email email = getEmail(EmailConstants.EMAIL_ORDER, to, "Order Created", VMConstants.VM_SEND_EMAIL_NOTIFICATION, order);

        try {
            sendEmail.sendEmail(email);
        } catch (MessagingException me) {
            log.error(" Error Sending email {}", me);
        }
    }

    public void sendAppStatusEmail()  throws AddressException{

        String to = "haigopi@gmail.com";
        Email email = getEmail(EmailConstants.EMAIL_CONTACT, to, "Application Restarted", VMConstants.VM_SEND_EMAIL_NOTIFICATION, null);

         try {
            sendEmail.sendEmail(email);
        } catch (MessagingException me) {
            log.error(" Error Sending email {}", me);
        }

    }

    public void sendMenuStatusUpdateEmail(Menu menu)  throws AddressException{

        String to = userRepository.findOne(menu.getUserName()).getEmail();
        Email email = getEmail(EmailConstants.EMAIL_CONTACT, to, "Application Restarted", VMConstants.VM_SEND_EMAIL_NOTIFICATION, null);

        try {
            sendEmail.sendEmail(email);
        } catch (MessagingException me) {
            log.error(" Error Sending email {}", me);
        }
    }
    public void sendMenuStatusUpdateEmail(String[] to) {

        // ToDo convert the usernames to emails. Query from DB.
        Email email = getEmail(EmailConstants.EMAIL_CONTACT, to, "Application Restarted", VMConstants.VM_SEND_EMAIL_NOTIFICATION, null);

        try {
            sendEmail.sendEmail(email);
        } catch (MessagingException me) {
            log.error(" Error Sending email {}", me);
        }
    }

    public void sendOrderStatusUpdateEmail(Order order) {

        String to = userRepository.findOne(order.getOrderedBy()).getEmail();
        Email email = getEmail(EmailConstants.EMAIL_CONTACT, to, "Application Restarted", VMConstants.VM_SEND_EMAIL_NOTIFICATION, null);

        try {
            sendEmail.sendEmail(email);
        } catch (MessagingException me) {
            log.error(" Error Sending email {}", me);
        }

    }


    public void sendContactusReplyEmail(String userName) {

        String to = userRepository.findOne(userName).getEmail();
        Email email = getEmail(EmailConstants.EMAIL_CONTACT, to, "Application Restarted", VMConstants.VM_SEND_EMAIL_NOTIFICATION, null);

        try {
            sendEmail.sendEmail(email);
        } catch (MessagingException me) {
            log.error(" Error Sending email {}", me);
        }

    }

    public void sendSupportReplyEmail(String userName) {

        String to = userRepository.findOne(userName).getEmail();
        Email email = getEmail(EmailConstants.EMAIL_CONTACT, to, "Application Restarted", VMConstants.VM_SEND_EMAIL_NOTIFICATION, null);

        try {
            sendEmail.sendEmail(email);
        } catch (MessagingException me) {
            log.error(" Error Sending email {}", me);
        }

    }
}
