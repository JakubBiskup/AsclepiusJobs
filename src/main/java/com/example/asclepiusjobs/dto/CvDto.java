package com.example.asclepiusjobs.dto;

public class CvDto {

    private String title;
    private String interests;
    private boolean available;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
