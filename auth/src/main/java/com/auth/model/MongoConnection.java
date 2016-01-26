package com.auth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by GeetaKrishna on 1/2/2016.
 */

@Document(collection = "connections")
@CompoundIndexes({
        @CompoundIndex(name = "primary", def = "{ 'userId' : 1, 'providerId' : 1, 'providerUserId' : 1 }", unique = true),
        @CompoundIndex(name = "rank", def = "{ 'userId' : 1, 'providerId' : 1, 'created' : 1 }", unique = true)
})

@Data
public class MongoConnection {

    @Id
    private String id;
    private Date created;

    private String userId;
    private String providerId;
    private String providerUserId;
    private int rank;
    private String displayName;
    private String profileUrl;
    private String imageUrl;
    @JsonIgnore
    private String accessToken;
    @JsonIgnore
    private String secret;
    @JsonIgnore
    private String refreshToken;
    @JsonIgnore
    private Long expireTime;

    public MongoConnection(String userId, String providerId, String providerUserId, int rank,
                                String displayName, String profileUrl, String imageUrl, String accessToken, String secret,
                                String refreshToken, Long expireTime) {
        super();
        this.userId = userId;
        this.providerId = providerId;
        this.providerUserId = providerUserId;
        this.rank = rank;
        this.displayName = displayName;
        this.profileUrl = profileUrl;
        this.imageUrl = imageUrl;
        this.accessToken = accessToken;
        this.secret = secret;
        this.refreshToken = refreshToken;
        this.expireTime = expireTime;
    }


}
