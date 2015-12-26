package com.vs.props;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
@Slf4j
@Component
@Data
public class ReadYML{

    @Autowired
    private EmailProperties emailPropertiesOri;

    @Autowired
    private RepoProperties repoPropertiesOri;

    public HashMap<String, String> getEmail() {
        return emailPropertiesOri.getEmail();
    }

    public HashMap<String, String> getRepos() {
        return repoPropertiesOri.getRepos();
    }

    public ReadYML() {
        log.info("{} object created {}", ReadYML.class.getName());
    }
}

@Component
@ConfigurationProperties(prefix = "vs")
@Data
class EmailProperties {
    private HashMap<String, String> email;
}

@Component
@ConfigurationProperties(prefix = "vs.mongo")
@Data
class RepoProperties {
    private HashMap<String, String> repos;
}
