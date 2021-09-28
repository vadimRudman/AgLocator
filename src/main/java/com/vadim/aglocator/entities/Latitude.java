package com.vadim.aglocator.entities;

public class Latitude {

    private final double latitude;

    private Latitude(double latitude) {
        this.latitude = latitude;
    };

    public static Latitude of(double latitude) {
        checkValid(latitude);
        return new Latitude(latitude);
    }

    private static void checkValid(double latitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90 " + latitude);
        }
    }
}
