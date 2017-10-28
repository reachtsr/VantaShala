package com.vs.model.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by GeetaKrishna on 12/23/2015.
 */
public enum Measurement {
    lt, kg, CUSTOM, COUNT;




    private static final List<Measurement> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Measurement randomMeasurment()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
