package com.vs.api.menu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by gopi on 10/22/16.
 */
public enum TestItemEnum {
    IDLI,
    VADA,
    BIRYANI,
    DOSA,
    DAHI,
    PERUGU,
    SAMABAR,
    EGG_CURRY,
    TOMATO_CURRY,
    VEG_BIRYANI,
    EGG_BIRYANI,
    CHICKEN_BIRYANI,
    PEANUT_CHUTNEY,
    KAKARAKAYA,
    POTLAKAYA_CURRY;

    private static final List<TestItemEnum> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static TestItemEnum randomItemName()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
