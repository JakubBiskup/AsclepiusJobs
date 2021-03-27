package com.example.asclepiusjobs.model.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherMain {
    private MainWeatherProperties main;

    public MainWeatherProperties getMain() {
        return main;
    }

    public void setMain(MainWeatherProperties main) {
        this.main = main;
    }
}
