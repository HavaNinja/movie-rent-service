package com.ahaveriuc.guards;

import lombok.experimental.UtilityClass;

@UtilityClass
public class NotNullGuard {
    public static <T> T notNull(T value, String name) {
        if (value == null) {
            throw new NullPointerException("%s must not be null".formatted(name));
        } else {
            return value;
        }
    }
}
