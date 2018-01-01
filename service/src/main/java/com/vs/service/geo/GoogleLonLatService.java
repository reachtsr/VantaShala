package com.vs.service.geo;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.vs.model.user.address.Address;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Created by GeetaKrishna on 30-Dec-17.
 **/
@Component
@Slf4j
public class GoogleLonLatService {

    String key = "AIzaSyBZiQ1eeuvAyu7jOLGzBJMHtuCe_5ExW-o";

    public LatLng getLatLon(Address businessAddress) {
        try {
            log.info("Address:{}", businessAddress.getPrintedAddress());
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(key)
                    .build();
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    businessAddress.getPrintedAddress()).await();
            return results[0].geometry.location;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LatLng getLatLonByZip(String zip) {
        try {
            log.info("ZIP:{}", zip);
            GeoApiContext context = new GeoApiContext.Builder()
                    .apiKey(key)
                    .build();
            GeocodingResult[] results = GeocodingApi.geocode(context,
                    zip).await();
            return results[0].geometry.location;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        GoogleLonLatService g = new GoogleLonLatService();
        g.getLatLonByZip("75034");
    }


}
