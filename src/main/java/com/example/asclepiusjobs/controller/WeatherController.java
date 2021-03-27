package com.example.asclepiusjobs.controller;

import com.example.asclepiusjobs.model.User;
import com.example.asclepiusjobs.model.weather.WeatherMain;
import com.example.asclepiusjobs.service.UserService;
import com.example.asclepiusjobs.service.WeatherService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @Autowired
    UserService userService;

    @GetMapping(value = "/weather/home")
    public ResponseEntity getWeatherAtHome() throws JsonProcessingException {
        String cityName=getAuthenticatedUser().getLocation().getCity();
        String weatherDescription=weatherService.getWeatherDescriptionInCity(cityName);
        return ResponseEntity.ok(weatherDescription);

    }

    private User getAuthenticatedUser(){
        String loggedInUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.getUserByEmail(loggedInUserEmail);
    }
}
