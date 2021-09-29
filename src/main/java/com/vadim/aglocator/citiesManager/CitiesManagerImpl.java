package com.vadim.aglocator.citiesManager;

import java.util.HashMap;
import java.util.Map;

import com.vadim.aglocator.entities.CityLocation;
import com.vadim.aglocator.entities.Latitude;
import com.vadim.aglocator.entities.Longitude;
import com.vadim.aglocator.entities.SearchResult;
import com.vadim.aglocator.searchmanager.SearchManager;
import com.vadim.aglocator.searchmanager.SearchManagerImpl;

public class CitiesManagerImpl implements CitiesManager {

    SearchManager searchManager = new SearchManagerImpl();

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

    @Override
    public Map<String, Object[]> getSearchResults(String query, String latitude, String longitude) {
        Latitude searchLatitude = parseSearchLatitude(latitude);
        Longitude searchLongitude = parseSearchLongitude(longitude);

        Map<String, CityLocation> unsortedUnscoredResults = searchManager.getUnsortedUnscoredResults(query);
        Map<String, SearchResult> unsortedScoredResults = searchManager.scoreUnsortedUnscoredResults(unsortedUnscoredResults, query, searchLatitude, searchLongitude);
        Map<String, SearchResult> sortedScoredResults = searchManager.sortUnsortedScoredResults(unsortedScoredResults);

        Map<String, Object[]> responseResults = new HashMap<>();
        responseResults.put("suggestions", sortedScoredResults.values().toArray());

        return responseResults;
    }
}
