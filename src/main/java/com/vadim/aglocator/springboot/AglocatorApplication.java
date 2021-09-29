package com.vadim.aglocator.springboot;

import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.vadim.aglocator.entities.CityLocation;
import com.vadim.aglocator.textparser.CitiesTextParser;
import com.vadim.aglocator.textparser.CitiesTextParserImpl;

@SpringBootApplication
public class AglocatorApplication {

	public static void main(String[] args) {
		SpringApplication.run(AglocatorApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println("Hello spring boot beans");
			CitiesTextParser citiesParser = new CitiesTextParserImpl();
			Map<String, CityLocation> canadianCities = citiesParser.parseCountryTextFile("src/main/resources/static/CA.zip");
			Map<String, CityLocation> americanCities = citiesParser.parseCountryTextFile("src/main/resources/static/US.zip");
		};
	}
}
