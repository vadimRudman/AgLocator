package com.vadim.aglocator.scorecalculator;

import com.vadim.aglocator.entities.CityLocation;
import com.vadim.aglocator.entities.Latitude;
import com.vadim.aglocator.entities.Longitude;

public interface ScoreCalculator {

    double calculateCityNameScore(String query, String cityName);

    double calculateLocationScore(CityLocation cityLocation, Latitude searchLatitude, Longitude searchLongitude);

    double calculateSearchScore(String query, String cityName, CityLocation cityLocation, Latitude searchLatitude, Longitude searchLongitude);
}
