package com.vadim.aglocator.scorecalculator;

import com.vadim.aglocator.entities.CityLocation;
import com.vadim.aglocator.entities.Latitude;
import com.vadim.aglocator.entities.Longitude;

public class ScoreCalculatorImpl implements ScoreCalculator {

    @Override
    public double calculateCityNameScore(String query, String cityName) {
        double score = (double)query.length() / cityName.length();
        return Math.round(score*100.0)/100.0;
    }

    @Override
    public double calculateLocationScore(CityLocation cityLocation, Latitude searchLatitude, Longitude searchLongitude) {
        //Do not factor location if theres no search lati or longi
        if (searchLatitude == null && searchLongitude == null) {
            return 1;
        }
        if (searchLatitude != null && searchLongitude == null) {
            return cityLocation.locationScore(searchLatitude);
        }
        //Longi must be not null to make it this far
        if (searchLatitude == null) {
            return cityLocation.locationScore(searchLongitude);
        }
        //Both search params must de defined to make it this far
        return cityLocation.locationScore(searchLatitude, searchLongitude);
    }
    @Override
    public double calculateSearchScore(String query, String cityName, CityLocation cityLocation, Latitude searchLatitude, Longitude searchLongitude) {
        double cityNameScore = calculateCityNameScore(query, cityName);
        double locationScore = calculateLocationScore(cityLocation, searchLatitude, searchLongitude);
        return cityNameScore * locationScore;
    }
}
