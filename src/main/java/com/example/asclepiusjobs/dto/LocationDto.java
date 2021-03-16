package com.example.asclepiusjobs.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LocationDto {

    private String street;
    private String house;
    private String apartment;
    private String postalCode;
    @NotNull
    @NotEmpty
    private String city;
    @NotNull
    @NotEmpty
    private String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getApartment() {
        return apartment;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
