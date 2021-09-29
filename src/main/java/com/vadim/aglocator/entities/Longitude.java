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

    public static Longitude of(String longitude) {
        checkValid(longitude);
        double parsedLongitude = Double.parseDouble(longitude);
        checkValid(parsedLongitude);
        return new Longitude(parsedLongitude);
    }

    public double getLongitude() {
        return longitude;
    }

    private static void checkValid(double longitude) {
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("Longitude must be between -180 and 180");
        }
    }

    private static void checkValid(String longitude) {
        try {
            Double.parseDouble(longitude);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Longitude must be a number " + longitude);
        }
    }

    @Override
    public String toString() {
        return "longitude=" + longitude;
    }
}
