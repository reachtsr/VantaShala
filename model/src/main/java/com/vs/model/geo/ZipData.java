package com.vs.model.geo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * Created by GeetaKrishna on 04-Dec-17.
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "zip_codes_usa")
public class ZipData {

    public static final String ID = "_id";
    public static final String CITY = "city";
    public static final String LOCATION = "loc";
    public static final String POPULATION = "pop";
    public static final String STATE = "state";

    @Id
    private final String _id;
    private final String city;
    private final double[] location;
    private final int population;
    private final String state;

    public ZipData(@JsonProperty(ID) String _id, @JsonProperty(CITY) String city, @JsonProperty(LOCATION) double[] location,
                   @JsonProperty(POPULATION) int population, @JsonProperty(STATE) String state) {
        this._id = _id;
        this.city = city;
        this.location = location;
        this.population = population;
        this.state = state;
    }

}
