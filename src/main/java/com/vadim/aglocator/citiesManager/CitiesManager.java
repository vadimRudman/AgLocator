package com.vadim.aglocator.citiesManager;

import java.util.ArrayList;
import java.util.Map;

import com.vadim.aglocator.entities.CityLocation;
import com.vadim.aglocator.entities.Latitude;
import com.vadim.aglocator.entities.Longitude;
import com.vadim.aglocator.entities.SearchResult;
import com.vadim.aglocator.textparser.CitiesTextParser;
import com.vadim.aglocator.textparser.CitiesTextParserImpl;

public interface CitiesManager {

    /**
     * Search based on just query
     * @param query
     * @return ArrayList of Search Results
     */
    public Map<String, CityLocation> getSearchResults(String query);

    /**
     * Search based on query and latitude
     * @param query
     * @return ArrayList of Search Results
     */
    public ArrayList<SearchResult> getSearchResults(String query, Latitude latitude);

    /**
     * Search based on query and longitude
     * @param query
     * @return ArrayList of Search Results
     */
    public ArrayList<SearchResult> getSearchResults(String query, Longitude longitude);

    /**
     * Search based on query, latitude and longitude
     * @param query
     * @return ArrayList of Search Results
     */
    public ArrayList<SearchResult> getSearchResults(String query, Latitude latitude, Longitude longitude);
}
