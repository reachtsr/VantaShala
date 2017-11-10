package com.vs.service.email;

import com.vs.common.constants.FreeMarkerConstants;
import com.vs.mail.ProcessEmail;
import com.vs.model.email.Email;
import com.vs.model.props.ReadYML;
import com.vs.repository.UserRepository;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by GeetaKrishna on 12/26/2015.
 */
@Slf4j
@Component
public abstract class CommonEmailService {

    @Autowired
    private Configuration freemarkerConfiguration;

    protected String mergeTemplateWithValues(String template, Object templateValues) {
        Map<String, Object> model = new HashMap<>();
        if (templateValues != null) {
            model.put(FreeMarkerConstants.VM_BEAN, templateValues);
        }
        String output = "";
        try {
            Template rTemplate = freemarkerConfiguration.getTemplate(template);
            output = FreeMarkerTemplateUtils.processTemplateIntoString(rTemplate, model);
        } catch (IOException e) {
            log.error("Error Freemarker Template:", e);
        } catch (TemplateException e) {
            log.error("Error Freemarker Template:", e);
        }
        return output;
    }

}
