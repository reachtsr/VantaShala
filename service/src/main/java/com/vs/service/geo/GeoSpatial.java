package com.vs.service.geo;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Component;

/**
 * Created by GeetaKrishna on 03-Dec-17.
 **/
@Component
public class GeoSpatial {


    public void findAllTownsWithinRadius10km() {
        //GIVEN
        int lowerLimit = 10 * 1000; //in meters

        //WHEN
//        Lists<ZipData> results = Lists.newArrayList(zipCodes.find("{loc: {$near : {$geometry : {type: 'Point', " +
//                "coordinates: [-122.252696, 37.900933] }, $maxDistance: # }}}", lowerLimit)
//                .as(ZipData.class).iterator());

        //THEN
        // assertThat(results).hasSize(19);
    }
}
