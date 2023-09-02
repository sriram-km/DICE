package com.dice.weatherApp.service;

import com.dice.weatherApp.utils.Util;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Properties;

@Service
public class WeatherService {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    Properties properties = Util.getApplicationProperties();

    private final RestTemplate restTemplate;
    private final String rapidApiKey = properties.getProperty("rapidapi.key");
    private final String rapidApiHost = properties.getProperty("rapidapi.host");

    private final String baseUrl = "https://"+rapidApiHost+"/rapidapi";

    public WeatherService() {
        this.restTemplate = new RestTemplate();
    }

    private HttpEntity getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-RapidAPI-Key", rapidApiKey);
        headers.set("X-RapidAPI-Host", rapidApiHost);
        return new HttpEntity<>(headers);
    }

    public ResponseEntity<String> getForecastSummaryByLocationName(String locationName) throws Exception {
        HttpEntity httpEntity = getHttpEntity();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/forecast/"+locationName+"/summary/");

        return getStringResponseEntity(httpEntity, builder);

    }

    public ResponseEntity<String> getHourlyForecastByLocationName(String locationName) throws Exception {
        HttpEntity httpEntity = getHttpEntity();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl + "/forecast/"+locationName+"/hourly/");

        return getStringResponseEntity(httpEntity, builder);

    }

    private ResponseEntity<String> getStringResponseEntity(HttpEntity httpEntity, UriComponentsBuilder builder) {
        try {
            // Forward the GET request to the external API and return the response as-is
            ResponseEntity<String> response = restTemplate.exchange(
                    builder.toUriString(), HttpMethod.GET, httpEntity, String.class);

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        } catch (HttpStatusCodeException e) {
            // Handle exceptions for non-2xx response codes
            return ResponseEntity.status(e.getRawStatusCode()).body(e.getResponseBodyAsString());
        }
    }
}
