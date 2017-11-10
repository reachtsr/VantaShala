package com.vs.service.email;

import com.vs.common.constants.FreeMarkerConstants;
import com.vs.mail.ProcessEmail;
import com.vs.model.email.Email;
import com.vs.model.menu.Menu;
import com.vs.model.order.Order;
import com.vs.model.props.EmailProperties;
import com.vs.model.props.ReadYML;
import com.vs.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by GeetaKrishna on 12/26/2015.
 */

@Slf4j
@Service
public class EmailService extends CommonEmailService {

    @Autowired
    ReadYML readYML;

    @Autowired
    EmailProperties emailProperties;


    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ProcessEmail processEmail;

    // ToDo Create VM Templates and update them accordingly.
    // ToDo Move String Constants to application yaml file and read them as needed.
    // ToDo Move haigopi@gmail.com to application yaml.

    public void sendOrderCreateEmail(Order order) {
        try {

            String message = mergeTemplateWithValues(FreeMarkerConstants.VM_SEND_ORDER_PLACED_EMAIL_NOTIFICATION, order);
            String to = userRepository.findOne(order.getOrderedBy()).getEmail();
            String from = emailProperties.getFromOrderEmail();
            String subject = "Order Placed";

            Email email = new Email.EmailBuilder(from, to, subject, message).build();
            processEmail.sendEmail(email);
        } catch (Exception me) {
            log.error(" Error Sending email {}", me);
        }
    }

    public void sendAppStatusEmail() {

        try {

            String from = emailProperties.getFromContactEmail();
            String to = "haigopi@gmail.com";
            String subject = "Application Restarted";
            String message = mergeTemplateWithValues(FreeMarkerConstants.VM_SEND_EMAIL_NOTIFICATION, "Application Restarted");


            Email email = new Email.EmailBuilder(from, to, subject, message).build();
            processEmail.sendEmail(email);
        } catch (Exception me) {
            log.error(" Error Sending email {}", me);
        }

    }

    public void sendMenuStatusUpdateEmail(Menu menu) {

        try {
            String to = userRepository.findOne(menu.getUserName()).getEmail();

        } catch (Exception me) {
            log.error(" Error Sending email {}", me);
        }
    }

    public void sendMenuStatusUpdateEmail(String[] to) {

        try {
            // ToDo convert the usernames to emails. Query from DB.

        } catch (Exception me) {
            log.error(" Error Sending email {}", me);
        }
    }

    public void sendOrderStatusUpdateEmail(Order order) {


        try {
            String to = userRepository.findOne(order.getOrderedBy()).getEmail();

        } catch (Exception me) {
            log.error(" Error Sending email {}", me);
        }

    }


    public void sendContactusReplyEmail(String userName) {


        try {
            String to = userRepository.findOne(userName).getEmail();

        } catch (Exception me) {
            log.error(" Error Sending email {}", me);
        }

    }

    public void sendSupportReplyEmail(String userName) {


        try {
            String to = userRepository.findOne(userName).getEmail();

        } catch (Exception me) {
            log.error(" Error Sending email {}", me);
        }

    }
}
