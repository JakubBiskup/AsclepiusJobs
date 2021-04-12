package com.example.asclepiusjobs.service;

import com.example.asclepiusjobs.model.Location;
import com.example.asclepiusjobs.model.weather.WeatherMain;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

@Service
public class WeatherService {

    private String apiKey=getApiKeyFromTextFile();

    public WeatherService() throws IOException {
    }

    public String getWeatherDescriptionInLocation(Location location) throws JsonProcessingException {
        return getWeatherMainInLocation(location).getMain().describeWeatherInString();
    }

    public WeatherMain getWeatherMainInLocation(Location location) throws JsonProcessingException {
        RestTemplateBuilder restTemplateBuilder=new RestTemplateBuilder();
        RestTemplate restTemplate=getRestTemplate(restTemplateBuilder);
        WeatherMain weatherMain = restTemplate.getForObject("https://api.openweathermap.org/data/2.5/weather?q="
                +location.getCity()
                +getCommaAndIsoCountryCode(location.getCountry())
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

    private String getCommaAndIsoCountryCode(String countryName){
        String[] isoCodes= Locale.getISOCountries();
        String isoCountryCode="";
        for(String code:isoCodes){
            Locale locale=new Locale("",code);
            if(countryName.equals(locale.getDisplayCountry())){
                isoCountryCode=","+code;
                break;
            }
        }
        return isoCountryCode;
    }

}
