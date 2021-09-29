package com.vadim.aglocator.citiesManager;

import java.util.Map;

public interface CitiesManager {

    /**
     * Search based on query, latitude and longitude
     * @param query
     * @return ArrayList of Search Results
     */
    public Map<String, Object[]> getSearchResults(String query, String latitude, String longitude);
}
