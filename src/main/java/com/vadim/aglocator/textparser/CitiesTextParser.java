package com.vadim.aglocator.textparser;

import java.util.Map;

import com.vadim.aglocator.entities.CityLocation;

public interface CitiesTextParser {

    /**
     *
     * @param textFilePath path to the country text file of cities
     * @return Map of string city name to entity CityLocation (Latitude Longitude)
     */
    Map<String, CityLocation> parseCountryTextFile(String textFilePath);

}
