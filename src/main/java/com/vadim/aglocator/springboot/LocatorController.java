package com.vadim.aglocator.springboot;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vadim.aglocator.citiesManager.CitiesManager;
import com.vadim.aglocator.citiesManager.CitiesManagerImpl;
import com.vadim.aglocator.entities.SearchResult;

@RestController
public class LocatorController {

    private CitiesManager citiesManager = new CitiesManagerImpl();

    @GetMapping("/suggestions")
    public ResponseEntity<?> searchForQuery(@RequestParam String q, @RequestParam(required = false) String latitude, @RequestParam(required = false) String longitude) {
        Map<String, Object[]> results = citiesManager.getSearchResults(q, latitude, longitude);
        return ResponseEntity.ok(results);
    }
}
