package com.vs.model.props;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
@ConfigurationProperties(prefix = "vs")
@Slf4j
@Data
public class EmailProperties {

    private HashMap<String, String> email;

    public String getFromOrderEmail() {
        return email.get("from.order");
    }

    public String getFromContactEmail() {
        return email.get("from.contact");
    }

    public String getReplyToEmail() {
        return email.get("replyTo");
    }

    public String getPassword() {
        return email.get("password");
    }


    @PostConstruct
    public void init() {
        log.info("Email Properties: {}", email);
    }
}
