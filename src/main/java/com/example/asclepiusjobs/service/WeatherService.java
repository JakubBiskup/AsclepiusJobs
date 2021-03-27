package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.model.weather.WeatherMain;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class WeatherService {

    private String apiKey=getApiKeyFromTextFile();

    public WeatherService() throws IOException {
    }

    public String getWeatherDescriptionInCity(String cityName) throws JsonProcessingException {
        return getWeatherMainInCity(cityName).getMain().describeWeatherInString();
    }

    public WeatherMain getWeatherMainInCity(String cityName) throws JsonProcessingException {
        RestTemplateBuilder restTemplateBuilder=new RestTemplateBuilder();
        RestTemplate restTemplate=getRestTemplate(restTemplateBuilder);
        WeatherMain weatherMain = restTemplate.getForObject("https://api.openweathermap.org/data/2.5/weather?q="
                +cityName
                +"&appid="
                + apiKey
                +"&units=metric",
                WeatherMain.class);

        return weatherMain;
    }


    private String getApiKeyFromTextFile() throws IOException {
        Path path = Path.of("../OpenWeatherMap.txt");
        return Files.readString(path);
    }

    private RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder.build();
    }

}
