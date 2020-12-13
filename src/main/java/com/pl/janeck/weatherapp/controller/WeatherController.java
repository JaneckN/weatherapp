package com.pl.janeck.weatherapp.controller;

import com.pl.janeck.weatherapp.model.Weather;
import com.pl.janeck.weatherapp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("weather/app/")
public class WeatherController {


    private RestTemplate restTemplate;
    private WeatherService weatherService;

    @Autowired
    public WeatherController(RestTemplate restTemplate, WeatherService weatherService) {
        this.restTemplate = restTemplate;
        this.weatherService = weatherService;
    }


    @GetMapping("/{location}")
    public ResponseEntity<Weather> getWeather(@PathVariable("location") String location) {
        Optional<Weather> weather = weatherService.getWeather(location);
        if (weather.isPresent()) {
            return ResponseEntity.ok(weather.get());
        }
        return ResponseEntity.notFound().build();
    }
}