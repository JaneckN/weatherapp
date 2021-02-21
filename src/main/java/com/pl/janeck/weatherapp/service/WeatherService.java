package com.pl.janeck.weatherapp.service;


import com.fasterxml.jackson.databind.JsonNode;
import com.pl.janeck.weatherapp.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;


@Service
public class WeatherService {

    private static final String WOEID_URL = "https://www.metaweather.com/api/location/";
    private static final String LOCATION_URL = "https://www.metaweather.com/api/location/search/?query=";
    private RestTemplate restTemplate;


    @Autowired
    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    private Integer getWoeid(String location) {
        Optional<JsonNode> jsonNode = Arrays.stream(Objects.requireNonNull(restTemplate.getForObject(LOCATION_URL + location, JsonNode[].class))).findFirst();
        if (jsonNode.isPresent()) {
            return jsonNode.get().get("woeid").asInt();
        } else {
            return 0;
        }
    }

    public Optional<Weather> getWeather(String location) {
        int woeid = getWoeid(location);
        return Optional.ofNullable(restTemplate.getForObject(WOEID_URL + woeid, Weather.class));

    }


}

