package com.vs.service.email;

import com.vs.common.constants.FreeMarkerConstants;
import com.vs.mail.ProcessEmail;
import com.vs.model.email.Email;
import com.vs.model.email.EmailGenerator;
import com.vs.model.menu.Menu;
import com.vs.model.order.CookMenuItem;
import com.vs.model.order.Order;
import com.vs.model.props.EmailProperties;
import com.vs.model.props.ReadYML;
import com.vs.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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
    @Async
    public void sendOrderCreateEmail(Order order) {
        try {

            String message = mergeTemplateWithValues(FreeMarkerConstants.VM_SEND_ORDER_PLACED_EMAIL_NOTIFICATION, getMap(order));
            String to = userRepository.findOne(order.getOrderedBy()).getEmail();
            String from = emailProperties.getFromOrderEmail();
            String subject = "Order Placed";

            EmailGenerator emailModel = new EmailGenerator.EmailBuilder(from, to, subject, message).build();
            processEmail.sendEmail(emailModel);



        } catch (Exception me) {
            log.error(" Error Sending email {}", me);
        }
    }

    @Async
    public void notifyCooks(Order order, String cookEmail, List<CookMenuItem> items) {
        try {

            HashMap<String, Object> map = getMap(items);
            map.put("order", order);
            log.info("Template: {}", map);
            String message = mergeTemplateWithValues(FreeMarkerConstants.VM_SEND_ORDER_PLACED_EMAIL_NOTIFICATION_TO_COOK, map);
            String from = emailProperties.getFromOrderEmail();
            String subject = "New Order in Place";

            EmailGenerator emailGenerator = new EmailGenerator.EmailBuilder(from, cookEmail, subject, message).build();
            processEmail.sendEmail(emailGenerator);

        } catch (Exception me) {
            log.error(" Error Sending email to cook {}", cookEmail, me);
        }
    }


    public void sendAppStatusEmail() {

        try {

            String from = emailProperties.getFromContactEmail();
            String to = "haigopi@gmail.com";
            String subject = "Application Restarted";
            String message = mergeTemplateWithValues(FreeMarkerConstants.VM_SEND_EMAIL_NOTIFICATION, getMap("Application Restarted"));


            EmailGenerator emailGenerator = new EmailGenerator.EmailBuilder(from, to, subject, message).build();
            processEmail.sendEmail(emailGenerator);
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
