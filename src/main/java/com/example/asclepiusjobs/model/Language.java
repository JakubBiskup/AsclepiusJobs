package com.example.asclepiusjobs.model;

import com.example.asclepiusjobs.model.enums.LanguageLevel;

import javax.persistence.*;

@Entity
public class Language {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private LanguageLevel level;

    @ManyToOne
    @JoinColumn(name = "cv_id",nullable = false)
    private Cv cv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Cv getCv() {
        return cv;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
    }
}
