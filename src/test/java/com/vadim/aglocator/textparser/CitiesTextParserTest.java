package com.vadim.aglocator.textparser;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.InvalidPathException;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.vadim.aglocator.entities.CityLocation;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CitiesTextParserTest {

    private static CitiesTextParser citiesTextParser;

    @BeforeAll
    public void init() {
        citiesTextParser = new CitiesTextParserImpl();
    }

    @Test
    public void test_success_ParseZipFile() {
        //TestCountry.zip has one text file like geonames zips
        //Like geonames text files, it contains locations of different formats seperated by tabs
        Map<String, CityLocation> testCities = citiesTextParser.parseCountryTextFile("src/test/resources/static/TestCountry.zip");

        String expectedCityOne = "Test City One";
        double expectedLatitudeOne = Double.parseDouble("46.42886");
        double expectedLongitudeOne = Double.parseDouble("-50.81995");

        String expectedCityTwo = "Test City Two";
        double expectedLatitudeTwo = Double.parseDouble("46.44743");
        double expectedLongitudeTwo = Double.parseDouble("-50.48577");

        int expectedMapSize = 2;

        assertEquals(expectedMapSize, testCities.size());

        assertTrue(testCities.containsKey(expectedCityOne));
        assertEquals(testCities.get(expectedCityOne).getLatitude().getLatitude(), expectedLatitudeOne);
        assertEquals(testCities.get(expectedCityOne).getLongitude().getLongitude(), expectedLongitudeOne);

        assertTrue(testCities.containsKey(expectedCityTwo));
        assertEquals(testCities.get(expectedCityTwo).getLatitude().getLatitude(), expectedLatitudeTwo);
        assertEquals(testCities.get(expectedCityTwo).getLongitude().getLongitude(), expectedLongitudeTwo);
    }

//    @Test
//    public void test_fail_IncorrectZipFilePath() {
//        InvalidPathException thrown = assertThrows(
//                InvalidPathException.class,
//                () -> citiesTextParser.parseCountryTextFile("incorrect/path/TestCountry.zip"));
//
//        assertTrue(thrown.getMessage().contains("incorrect/path/TestCountry.zip"));
//    }

}
