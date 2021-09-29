package com.vadim.aglocator.textparser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.vadim.aglocator.entities.CityLocation;
import com.vadim.aglocator.entities.Latitude;
import com.vadim.aglocator.entities.Longitude;

public class CitiesTextParserImpl implements CitiesTextParser {

    private ArrayList<String> extractRawLocations(String textFilePath) throws IOException {
        try {
            ZipFile zipFile = new ZipFile(textFilePath);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            ArrayList<String> lines = new ArrayList<>();

            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);
                InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                Scanner inputStream = new Scanner(reader);

                while (inputStream.hasNext()) {
                    String strLine = inputStream.nextLine();
                    lines.add(strLine);
                }

                inputStream.close();
                stream.close();
            }
            zipFile.close();
            return lines;
        } catch (NoSuchFileException e) {
            throw new NoSuchFileException(textFilePath);
        }
    }

    private boolean isRawLocationACity(String rawLocation) {
        String[] locationComponents = rawLocation.split("\\t+");
        boolean isCity = Arrays.stream(locationComponents)
                .anyMatch(s -> s.matches("^P$"));
        return isCity;
    }

    private List<String> filterRawLocations(ArrayList<String> rawLocations) {
        return rawLocations
                .stream()
                .filter(rawLocation -> isRawLocationACity(rawLocation))
                .collect(Collectors.toList());
    }

    private String getCityNameFromRawCity(String rawCity) {
        String[] cityComponents = rawCity.split("\\t+");
        return cityComponents[1];
    }

    private CityLocation getCityLocationFromRawCity(String rawCity) {
        //Regex matching lat, lon pattern
        String latLonRegex = "^((\\-?|\\+?)?\\d{1,3}+(\\.\\d{1,6})?)";

        String[] cityComponents = rawCity.split("\\t+");
        List<String> latLongList = Arrays.asList(cityComponents)
                .stream()
                .filter(s -> s.matches(latLonRegex))
                .collect(Collectors.toList());

        return CityLocation.of(Latitude.of(latLongList.get(0)), Longitude.of(latLongList.get(1)));
    }

    @Override
    public Map<String, CityLocation> parseCountryTextFile(String textFilePath) {
        ArrayList<String> rawLocations = null;
        try {
            rawLocations = extractRawLocations(textFilePath);
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (rawLocations == null) {
            return new HashMap<>();
        }
        ArrayList<String> rawCities = new ArrayList<>(filterRawLocations(rawLocations));
        Map<String, CityLocation> parsedCities = new HashMap<>();
        rawCities.forEach(rawCity -> parsedCities.put(getCityNameFromRawCity(rawCity), getCityLocationFromRawCity(rawCity)));
        return parsedCities;
    }
}
