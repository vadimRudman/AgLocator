package com.vadim.aglocator.entities;

public class CityLocation {
    private Latitude latitude;
    private Longitude longitude;

    private CityLocation(Latitude latitude, Longitude longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static CityLocation of(Latitude latitude, Longitude longitude) {
        return new CityLocation(latitude, longitude);
    }
}
