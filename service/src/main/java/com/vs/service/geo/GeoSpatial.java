package com.vs.service.geo;

import com.google.maps.model.LatLng;
import com.vs.model.enums.Country;
import com.vs.model.user.Cook;
import com.vs.model.user.User;
import com.vs.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by GeetaKrishna on 03-Dec-17.
 **/
@Component
@Slf4j
public class GeoSpatial {

//    @Autowired
//    USZipCodesRepository usZipCodesRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MongoTemplate template;

    @Autowired
    GoogleLonLatService googleLonLatService;

//    //public ZipData getCoOrdinates(String zipCode) {
//        return usZipCodesRepository.findBy_id(zipCode);
//    }

    public List<Cook> getCooksNearBy(Country country, double[] location, int miles) {
        log.info("User: {} searching for cooks near by: {} miles");
        Point point = new Point(location[1], location[0]);
        NearQuery query = NearQuery.near(point).maxDistance(new Distance(miles, Metrics.MILES));
        GeoResults<User> cooks = template.geoNear(query, User.class);
        return filterUsers(cooks, country);
    }

    public List<Cook> getCooksNearBy(Country country, User user, int miles) {
        log.info("User: {} searching for cooks near by: {} miles");
        Point point = new Point(user.getLoc().getX(), user.getLoc().getY());
        NearQuery query = NearQuery.near(point).maxDistance(new Distance(miles, Metrics.MILES));
        GeoResults<User> cooks = template.geoNear(query, User.class);
        return filterUsers(cooks, country);
    }

    public List<Cook> getCooksNearBy(Country country, String zipCode, int miles) {
        log.info("ZIP: {} searching for cooks near by: {} miles", zipCode, miles);
        LatLng latLng = googleLonLatService.getLatLonByZip(zipCode);
        //ZipData zipData = usZipCodesRepository.findBy_id(zipCode);
        Point point = new Point(latLng.lng, latLng.lat);
        log.info("Point: {}", point);
        NearQuery query = NearQuery.near(point).maxDistance(new Distance(miles, Metrics.MILES));
        GeoResults<User> cooks = template.geoNear(query, User.class);
        return filterUsers(cooks, country);

    }

    private List<Cook> filterUsers(GeoResults<User> cooks, Country country) {

        List<Cook> filteredCooks = new ArrayList<>();

        cooks.forEach(u -> {
            User user = u.getContent();
            if (user instanceof Cook) {
                Cook cook = (Cook) u.getContent();
                if (cook.getBusinessAddress().getCountry() == country) {
                    filteredCooks.add(cook);
                }
            }
        });

        return filteredCooks;
    }

}
