package com.vadim.aglocator.entities;

public class CityLocation {
    private final Latitude latitude;
    private final Longitude longitude;

    private CityLocation(Latitude latitude, Longitude longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Latitude getLatitude() {
        return latitude;
    }

    public Longitude getLongitude() {
        return longitude;
    }

    public static CityLocation of(Latitude latitude, Longitude longitude) {
        return new CityLocation(latitude, longitude);
    }

    public double locationScore(Latitude latitude) {
        final double MAX_LATITUDE_DIFFERENCE = 180;
        //To calculate location score first find the absolute difference between search and actual
        //Then, get the ratio between the variance from max and the max
        double locationDifference = Math.abs(latitude.getLatitude() - this.latitude.getLatitude());
        double score = (MAX_LATITUDE_DIFFERENCE - locationDifference) / MAX_LATITUDE_DIFFERENCE;
        return Math.round(score*100.0)/100.0;
    }

    public double locationScore(Longitude longitude) {
        final double MAX_LONGITUDE_DIFFERENCE = 360;
        double locationDifference = Math.abs(longitude.getLongitude() - this.longitude.getLongitude());
        double score = (MAX_LONGITUDE_DIFFERENCE - locationDifference) / MAX_LONGITUDE_DIFFERENCE;
        return Math.round(score*100.0)/100.0;
    }

    public double locationScore(Latitude latitude, Longitude longitude) {
        return locationScore(latitude) * locationScore(longitude);
    }

    @Override
    public String toString() {
        return latitude.toString() + "\n" + longitude.toString();
    }
}
