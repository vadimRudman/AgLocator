package com.vadim.aglocator.citiesManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.vadim.aglocator.entities.CityLocation;
import com.vadim.aglocator.entities.Latitude;
import com.vadim.aglocator.entities.Longitude;
import com.vadim.aglocator.entities.SearchResult;
import com.vadim.aglocator.textparser.CitiesTextParser;
import com.vadim.aglocator.textparser.CitiesTextParserImpl;

public class CitiesManagerImpl implements CitiesManager {

    private CitiesTextParser citiesParser = new CitiesTextParserImpl();
    private Map<String, CityLocation> canadianCities = citiesParser.parseCountryTextFile("src/main/resources/static/CA.txt");
    private Map<String, CityLocation> americanCities = citiesParser.parseCountryTextFile("src/main/resources/static/US.txt");

    private Map<String, CityLocation> getCitiesThatStartWith(String query) {
        Map<String, CityLocation> citiesThatStartWith = new HashMap<>();

        Map<String, CityLocation> canadianResults = canadianCities
                .entrySet()
                .stream()
                .filter(cityEntry -> cityEntry.getKey().startsWith(query))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        Map<String, CityLocation> americanResults = americanCities
                .entrySet()
                .stream()
                .filter(cityEntry -> cityEntry.getKey().startsWith(query))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        citiesThatStartWith.putAll(canadianResults);
        citiesThatStartWith.putAll(americanResults);

        return citiesThatStartWith;
    }

    public Map<String, CityLocation> getSearchResults(String query) {
        Map<String, CityLocation> citiesThatStartWith = getCitiesThatStartWith(query);
        return citiesThatStartWith;
    }

    public ArrayList<SearchResult> getSearchResults(String query, Latitude latitude) {
        return new ArrayList<>();
    }

    public ArrayList<SearchResult> getSearchResults(String query, Longitude longitude) {
        return new ArrayList<>();
    }

    public ArrayList<SearchResult> getSearchResults(String query, Latitude latitude, Longitude longitude) {
        return new ArrayList<>();
    }
}
