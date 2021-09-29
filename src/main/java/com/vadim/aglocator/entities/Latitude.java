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

    public static Latitude of(String latitude) {
        checkValid(latitude);
        double parsedLatitude = Double.parseDouble(latitude);
        checkValid(parsedLatitude);
        return new Latitude(parsedLatitude);
    }

    public double getLatitude() {
        return latitude;
    }

    private static void checkValid(double latitude) {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("Latitude must be between -90 and 90 " + latitude);
        }
    }

    private static void checkValid(String latitude) {
        try {
            Double.parseDouble(latitude);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Latitude must be a number " + latitude);
        }
    }

    @Override
    public String toString() {
        return "latitude=" + latitude;
    }
}
