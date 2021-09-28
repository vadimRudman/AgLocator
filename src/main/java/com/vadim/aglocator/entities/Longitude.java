package com.vadim.aglocator.entities;

public class Longitude {

    private final double longitude;

    private Longitude(double longitude) {
        this.longitude = longitude;
    };

    public static Longitude of(double longitude) {
        checkValid(longitude);
        return new Longitude(longitude);
    }

    private static void checkValid(double longitude) {
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
    }
}
