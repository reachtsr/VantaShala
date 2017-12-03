package com.vs.service.email;

import com.vs.common.constants.FreeMarkerConstants;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

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

    protected String mergeTemplateWithValues(String template, Map<String, Object> model) {

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

    protected HashMap<String, Object> getMap(Object object) {
        HashMap<String, Object> map = new HashMap<>();
        map.put(FreeMarkerConstants.VM_BEAN, object);
        return map;
    }
}
