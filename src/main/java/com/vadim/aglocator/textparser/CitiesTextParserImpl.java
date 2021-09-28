package com.vadim.aglocator.textparser;

import static java.util.stream.Collectors.toMap;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.vadim.aglocator.entities.CityLocation;
import com.vadim.aglocator.entities.Latitude;
import com.vadim.aglocator.entities.Longitude;

public class CitiesTextParserImpl implements CitiesTextParser {

    private ArrayList<String> extractRawLocations(String textFilePath) {
        FileInputStream stream = null;
        try {
            stream = new FileInputStream(textFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String strLine;
        ArrayList<String> lines = new ArrayList<String>();
        try {
            while ((strLine = reader.readLine()) != null) {
                lines.add(strLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private boolean isRawLocationACity(String rawLocation) {
        String[] locationComponents = rawLocation.split("\\t+");
        boolean isCity = Arrays.asList(locationComponents)
                .stream()
                .anyMatch(s -> s.matches("^P$"));
        return isCity;
    }

    private List<String> filterRawLocations(ArrayList<String> rawLocations) {
        return rawLocations
                .stream()
                .filter(rawLocation -> isRawLocationACity(rawLocation))
                .collect(Collectors.toList());
    }

    private String getCityNameFromRawCity(String rawCity) {
        String[] cityComponents = rawCity.split("\\t+");
        return cityComponents[1];
    }

    private CityLocation getCityLocationFromRawCity(String rawCity) {
        //Regex matching lat, lon pattern
        String latLonRegex = "^((\\-?|\\+?)?\\d{1,3}+(\\.\\d{1,6})?)";

        String[] cityComponents = rawCity.split("\\t+");
        List<String> latLongList = Arrays.asList(cityComponents)
                .stream()
                .filter(s -> s.matches(latLonRegex))
                .collect(Collectors.toList());

        return CityLocation.of(Latitude.of(Double.parseDouble(latLongList.get(0))), Longitude.of(Double.parseDouble(latLongList.get(1))));
    }

    @Override
    public Map<String, CityLocation> parseCountryTextFile(String textFilePath) {
        ArrayList<String> rawLocations = extractRawLocations(textFilePath);
        ArrayList<String> rawCities = new ArrayList<>(filterRawLocations(rawLocations));
        Map<String, CityLocation> parsedCities = new HashMap<>();
        rawCities.forEach(rawCity -> parsedCities.put(getCityNameFromRawCity(rawCity), getCityLocationFromRawCity(rawCity)));
        return parsedCities;
    }
}
