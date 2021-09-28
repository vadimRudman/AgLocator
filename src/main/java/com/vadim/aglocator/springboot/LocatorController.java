package com.vadim.aglocator.springboot;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vadim.aglocator.citiesManager.CitiesManager;
import com.vadim.aglocator.citiesManager.CitiesManagerImpl;
import com.vadim.aglocator.entities.CityLocation;
import com.vadim.aglocator.entities.SearchResult;

@RestController
public class LocatorController {

    private CitiesManager citiesManager = new CitiesManagerImpl();

    @GetMapping("/suggestions")
    public String searchFor(@RequestParam String q) {
        Map<String, CityLocation> results = citiesManager.getSearchResults(q);
        return results.toString();
    }
}
