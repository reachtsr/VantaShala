package com.vs.model.user.address;

import com.vs.model.enums.Country;
import lombok.Data;

/**
 * Created by GeetaKrishna on 12/22/2015.
 */
@Data
public class Address {
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zipCode;
    private Country country = Country.US;


    public String getPrintedAddress() {
        return
                address1 + "," + address2 + ", " + city + ", " +
                        state + ", " +
                        zipCode + ", " +
                        country;

    }
}
