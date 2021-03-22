package com.example.asclepiusjobs.dto;

import com.example.asclepiusjobs.model.enums.LanguageLevel;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class LanguageDto {
    @NotNull
    @NotEmpty
    private String name;
    private LanguageLevel level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LanguageLevel getLevel() {
        return level;
    }

    public void setLevel(LanguageLevel level) {
        this.level = level;
    }
}
