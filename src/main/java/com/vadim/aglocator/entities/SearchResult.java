package com.vadim.aglocator.entities;

public class SearchResult implements Comparable<SearchResult> {

    private String cityName;
    private CityLocation cityLocation;
    private double searchScore;

    private SearchResult(String cityName, CityLocation cityLocation, double searchScore) {
        this.cityName = cityName;
        this.cityLocation = cityLocation;
        this.searchScore = Math.round(searchScore*10000.0)/10000.0;
    }

    public String getCityName() {
        return cityName;
    }

    public CityLocation getCityLocation() {
        return cityLocation;
    }

    public double getSearchScore() {
        return searchScore;
    }

    public static SearchResult of(String cityName, CityLocation cityLocation, double searchScore) {
        return new SearchResult(cityName, cityLocation, searchScore);
    }

    @Override
    public int compareTo(SearchResult compareSearchResult) {
        if (searchScore < compareSearchResult.getSearchScore()) {
            return -1;
        }
        if (searchScore > compareSearchResult.getSearchScore()) {
            return 1;
        }
        return 0;
    }
}
