package com.vs.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public enum Measurment {
    lt, kg, CUSTOM, COUNT;




    private static final List<Measurment> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Measurment randomMeasurment()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
