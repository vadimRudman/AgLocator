package com.vadim.aglocator.searchmanager;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.vadim.aglocator.entities.CityLocation;
import com.vadim.aglocator.entities.Latitude;
import com.vadim.aglocator.entities.Longitude;
import com.vadim.aglocator.entities.SearchResult;

public interface SearchManager {

    Map<String, CityLocation> getUnsortedUnscoredResults(String query);

    Map<String, SearchResult> scoreUnsortedUnscoredResults(Map<String, CityLocation> unsortedUnscoredResults, String query,
                                                           Latitude searchLatitude, Longitude searchLongitude);

    Map<String, SearchResult> sortUnsortedScoredResults(Map<String, SearchResult> unsortedScoredResults);
}
