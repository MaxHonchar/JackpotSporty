package com.sporty.test.utils;

import java.util.Optional;

public class JackpotUtils {

    public static final int HUNDRED = 100;
    public static final Double ZERO = 0.0;
    public static final Double ONE = 1.0;

    public static Double getPercentage(Double value) {
        return Optional.ofNullable(value)
                .map(val -> val/HUNDRED)
                .orElse(ZERO);
    }
}
