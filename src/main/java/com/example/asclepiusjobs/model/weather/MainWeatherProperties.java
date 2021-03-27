package com.example.asclepiusjobs.model.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MainWeatherProperties {
    private float temp;
    private float feels_like;
    private int pressure;
    private int humidity;

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public float getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(float feels_like) {
        this.feels_like = feels_like;
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
        stringBuilder.append(this.feels_like);
        stringBuilder.append(". Humidity: ");
        stringBuilder.append(this.humidity);
        stringBuilder.append("% .Pressure: ");
        stringBuilder.append(getPressure());
        stringBuilder.append(" hPa.");
        return stringBuilder.toString();
    }
}
