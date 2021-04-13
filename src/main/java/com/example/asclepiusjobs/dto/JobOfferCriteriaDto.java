package com.example.asclepiusjobs.dto;

import com.example.asclepiusjobs.model.enums.Profession;

import java.util.List;

public class JobOfferCriteriaDto {
    private String country;
    private String city;
    private String title;
    private Integer maxDaysSinceCreation;
    private Integer minExperience;
    private Integer maxExperience;
    private Integer minSalary;
    private Integer maxSalary;
    private String healthEstablishmentName;
    private Boolean showOlderFirst;
    private Pagination pagination;
    private List<Profession> professions;



    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMaxDaysSinceCreation() {
        return maxDaysSinceCreation;
    }

    public void setMaxDaysSinceCreation(Integer maxDaysSinceCreation) {
        this.maxDaysSinceCreation = maxDaysSinceCreation;
    }

    public Integer getMinExperience() {
        return minExperience;
    }

    public void setMinExperience(Integer minExperience) {
        this.minExperience = minExperience;
    }

    public Integer getMaxExperience() {
        return maxExperience;
    }

    public void setMaxExperience(Integer maxExperience) {
        this.maxExperience = maxExperience;
    }

    public Integer getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Integer minSalary) {
        this.minSalary = minSalary;
    }

    public Integer getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Integer maxSalary) {
        this.maxSalary = maxSalary;
    }

    public String getHealthEstablishmentName() {
        return healthEstablishmentName;
    }

    public void setHealthEstablishmentName(String healthEstablishmentName) {
        this.healthEstablishmentName = healthEstablishmentName;
    }

    public Boolean getShowOlderFirst() {
        return showOlderFirst;
    }

    public void setShowOlderFirst(Boolean showOlderFirst) {
        this.showOlderFirst = showOlderFirst;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Profession> getProfessions() {
        return professions;
    }

    public void setProfessions(List<Profession> professions) {
        this.professions = professions;
    }
}

