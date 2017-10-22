package com.vs.model.props;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
@ConfigurationProperties(prefix = "vs.mongo")
@Slf4j
public class RepoProperties {
    private HashMap<String, String> repos;

    public HashMap<String, String> getRepos() {
        return repos;
    }

    public void setRepos(HashMap<String, String> repos) {
        this.repos = repos;
    }

    @PostConstruct
    public void init() {
        log.info("Repo Properties: {}", repos);
    }
}
