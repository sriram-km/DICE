package com.dice.weatherApp.controller;

import com.dice.weatherApp.service.WeatherService;
import com.dice.weatherApp.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/summary")
    public ResponseEntity<String> getForecastSummary(@RequestParam String locationName) {
        try {
            return weatherService.getForecastSummaryByLocationName(locationName);
        } catch (Exception e) {
            return Constants.INTERNAL_SERVER_ERROR;
        }
    }

    @GetMapping("/hourly")
    public ResponseEntity<String> getHourlyForecast(@RequestParam String locationName) {
        try {
            return weatherService.getHourlyForecastByLocationName(locationName);
        }  catch (Exception e) {
            return Constants.INTERNAL_SERVER_ERROR;
        }
    }
}

