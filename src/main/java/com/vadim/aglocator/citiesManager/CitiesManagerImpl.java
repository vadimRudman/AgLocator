package com.vadim.aglocator.citiesManager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.vadim.aglocator.entities.CityLocation;
import com.vadim.aglocator.entities.Latitude;
import com.vadim.aglocator.entities.Longitude;
import com.vadim.aglocator.entities.SearchResult;
import com.vadim.aglocator.textparser.CitiesTextParser;
import com.vadim.aglocator.textparser.CitiesTextParserImpl;

public class CitiesManagerImpl implements CitiesManager {

    private final CitiesTextParser citiesParser = new CitiesTextParserImpl();
    private final Map<String, CityLocation> canadianCities = citiesParser.parseCountryTextFile("src/main/resources/static/CA.zip");
    private final Map<String, CityLocation> americanCities = citiesParser.parseCountryTextFile("src/main/resources/static/US.zip");

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

    private double calculateCityNameScore(String query, String cityName) {
        double score = (double)query.length() / cityName.length();
        return Math.round(score*100.0)/100.0;
    }

    private double calculateLocationScore(CityLocation cityLocation, Latitude searchLatitude, Longitude searchLongitude) {
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

    private double calculateSearchScore(String query, String cityName, CityLocation cityLocation, Latitude searchLatitude, Longitude searchLongitude) {
        double cityNameScore = calculateCityNameScore(query, cityName);
        double locationScore = calculateLocationScore(cityLocation, searchLatitude, searchLongitude);
        return cityNameScore * locationScore;
    }

    private Latitude parseSearchLatitude(String latitude) {
        if (latitude == null) {
            return null;
        }
        return Latitude.of(latitude);
    }

    private Longitude parseSearchLongitude(String longitude) {
        if (longitude == null) {
            return null;
        }
        return Longitude.of(longitude);
    }

    private Map<String, CityLocation> getUnsortedUnscoredResults(String query) {
        return getCitiesThatStartWith(query);
    }

    private Map<String, SearchResult> scoreUnsortedUnscoredResults(Map<String, CityLocation> unsortedUnscoredResults, String query, Latitude searchLatitude, Longitude searchLongitude) {
        return unsortedUnscoredResults.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> SearchResult.of(entry.getKey(), entry.getValue(), calculateSearchScore(query, entry.getKey(), entry.getValue(), searchLatitude, searchLongitude))));
    }

    private Map<String, SearchResult> sortUnsortedScoredResults(Map<String, SearchResult> unsortedScoredResults) {
        return unsortedScoredResults.entrySet()
                .stream()
                .sorted(Map.Entry.<String, SearchResult>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (entry1, entry2) -> entry1, LinkedHashMap::new));
    }

    @Override
    public Map<String, Object[]> getSearchResults(String query, String latitude, String longitude) {
        Latitude searchLatitude = parseSearchLatitude(latitude);
        Longitude searchLongitude = parseSearchLongitude(longitude);
        Map<String, CityLocation> unsortedUnscoredResults = getUnsortedUnscoredResults(query);
        Map<String, SearchResult> unsortedScoredResults = scoreUnsortedUnscoredResults(unsortedUnscoredResults, query, searchLatitude, searchLongitude);
        Map<String, SearchResult> sortedScoredResults = sortUnsortedScoredResults(unsortedScoredResults);

        Map<String, Object[]> responseResults = new HashMap<>();
        responseResults.put("suggestions", sortedScoredResults.values().toArray());
        return responseResults;
    }
}
