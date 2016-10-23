package com.vs.service.email;

import com.vs.common.constants.VMConstants;
import com.vs.mail.ProcessEmail;
import com.vs.model.email.Email;
import com.vs.props.ReadYML;
import com.vs.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by GeetaKrishna on 12/26/2015.
 */
@Slf4j
public abstract class CommonEmailService implements ApplicationContextAware {

    //ToDo Add Customer to Cook email communication.
    private ApplicationContext applicationContext;

    @Autowired
    protected VelocityEngine velocityEngine;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ProcessEmail processEmail;

    @Autowired
    protected ReadYML readYML;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private Email getEmail(final String fromEmail, String subject, String template, Object templateValues)  throws AddressException{
        Email email = (Email) applicationContext.getBean("email");
        email.setFromEmail(new InternetAddress(readYML.getEmail().get(fromEmail)));
        email.setSubject(subject);
        email.setMessage(mergeTemplateWithValues(template, templateValues));
        return email;
    }


    protected Email getEmail(final String fromEmail, String to, String subject, String template, Object templateValues)  {
        try {
            Email email = getEmail(fromEmail, subject, template, templateValues);
            email.setTo(to);
            return email;
        } catch (AddressException ae) {
            log.error("Error Creating Email: {}", ae);
        }
        return null;
    }

    protected Email getEmail(final String fromEmail, String[] to, String subject, String template, Object templateValues)  {
        try {
            Email email = getEmail(fromEmail, subject, template, templateValues);
            email.setTo(to);
            return email;
        } catch (AddressException ae) {
            log.error("Error Creating Email: {}", ae);
        }
        return null;
    }

    private String mergeTemplateWithValues(String template, Object templateValues) {
        Map<String, Object> model = new HashMap<>();
        if(templateValues!=null) {
            model.put(VMConstants.VM_BEAN, templateValues);
        }
        return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, template, "UTF-8", model);
    }


}
