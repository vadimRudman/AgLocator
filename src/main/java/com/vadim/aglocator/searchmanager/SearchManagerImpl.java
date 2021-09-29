package com.vadim.aglocator.searchmanager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.vadim.aglocator.entities.CityLocation;
import com.vadim.aglocator.entities.Latitude;
import com.vadim.aglocator.entities.Longitude;
import com.vadim.aglocator.entities.SearchResult;
import com.vadim.aglocator.scorecalculator.ScoreCalculator;
import com.vadim.aglocator.scorecalculator.ScoreCalculatorImpl;
import com.vadim.aglocator.textparser.CitiesTextParser;
import com.vadim.aglocator.textparser.CitiesTextParserImpl;

public class SearchManagerImpl implements SearchManager {

    private final CitiesTextParser citiesParser = new CitiesTextParserImpl();
    private final Map<String, CityLocation> canadianCities = citiesParser.parseCountryTextFile("src/main/resources/static/CA.zip");
    private final Map<String, CityLocation> americanCities = citiesParser.parseCountryTextFile("src/main/resources/static/US.zip");

    private final ScoreCalculator scoreCalculator = new ScoreCalculatorImpl();

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

    @Override
    public Map<String, CityLocation> getUnsortedUnscoredResults(String query) {
        return getCitiesThatStartWith(query);
    }

    @Override
    public Map<String, SearchResult> scoreUnsortedUnscoredResults(Map<String, CityLocation> unsortedUnscoredResults, String query, Latitude searchLatitude, Longitude searchLongitude) {
        return unsortedUnscoredResults.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        entry -> SearchResult.of(entry.getKey(), entry.getValue(), scoreCalculator.calculateSearchScore(query, entry.getKey(), entry.getValue(), searchLatitude, searchLongitude))));
    }

    @Override
    public Map<String, SearchResult> sortUnsortedScoredResults(Map<String, SearchResult> unsortedScoredResults) {
        return unsortedScoredResults.entrySet()
                .stream()
                .sorted(Map.Entry.<String, SearchResult>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue, (entry1, entry2) -> entry1, LinkedHashMap::new));
    }

}
