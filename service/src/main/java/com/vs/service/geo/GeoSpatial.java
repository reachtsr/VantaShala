package com.vs.service.geo;

import com.vs.model.geo.ZipData;
import com.vs.model.user.User;
import com.vs.repository.USZipCodesRepository;
import com.vs.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Component;

/**
 * Created by GeetaKrishna on 03-Dec-17.
 **/
@Component
@Slf4j
public class GeoSpatial {

    @Autowired
    USZipCodesRepository usZipCodesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoTemplate template;

    public ZipData getCoOrdinates(String zipCode) {
        return usZipCodesRepository.findBy_id(zipCode);
    }

    public GeoResults<User> getUsersNearBy(User user, int miles) {
        log.info("User: {} searching for cooks near by: {} miles");
        Point point = new Point(user.getLocation()[0], user.getLocation()[1]);
        NearQuery query = NearQuery.near(point).maxDistance(new Distance(miles, Metrics.MILES));
        GeoResults<User> cooks = template.geoNear(query, User.class);
        return cooks;
    }


}
