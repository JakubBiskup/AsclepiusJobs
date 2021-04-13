package com.example.asclepiusjobs.model.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainWeatherProperties {
    private float temp;
    private float feelsLike;
    private int pressure;
    private int humidity;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(float feelsLike) {
        this.feelsLike = feelsLike;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String describeWeatherInString(){
        StringBuilder stringBuilder=new StringBuilder("Temperature (Celsius): ");
        stringBuilder.append(this.getTemp());
        stringBuilder.append(", feels like: ");
        stringBuilder.append(this.feelsLike);
        stringBuilder.append(". Humidity: ");
        stringBuilder.append(this.humidity);
        stringBuilder.append("% .Pressure: ");
        stringBuilder.append(getPressure());
        stringBuilder.append(" hPa.");
        return stringBuilder.toString();
    }
}
