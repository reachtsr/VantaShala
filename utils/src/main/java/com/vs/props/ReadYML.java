package com.vs.props;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * Created by GeetaKrishna on 12/24/2015.
 */
@Slf4j
@Component
@Data
public class ReadYML{

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private RepoProperties repoProperties;

    public HashMap<String, String> getEmail() {
        return emailProperties.getEmail();
    }

    public HashMap<String, String> getRepos() {
        return repoProperties.getRepos();
    }

    @PostConstruct
    public void init(){
        log.info("{} object initialized.", ReadYML.class.getName());
        log.info("Read Email Properties: {}", emailProperties);
        log.info("Read Repo Properties: {}", repoProperties);
    }
    public ReadYML() {

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
