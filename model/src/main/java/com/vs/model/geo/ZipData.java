package com.vs.model.geo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * Created by GeetaKrishna on 04-Dec-17.
 **/
@Data
@Document(collection = "zip_codes_usa")
public class ZipData {

    @Id
    private String _id;
    private String city;
    private double[] loc;
    private int pop;
    private String state;

}
