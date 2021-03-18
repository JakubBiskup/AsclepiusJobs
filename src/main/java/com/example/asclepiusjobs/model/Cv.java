package com.example.asclepiusjobs.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
public class Cv {
    @Id
    @Column(name = "user_id")
    private Long id;

    private String title;
    private String interests;
    private boolean available;
    @NotNull
    private boolean visibility;
    @Column(name = "photo_path")
    private String photoPath;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "cv_skill",
            joinColumns = @JoinColumn(name = "cv_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;

    @OneToMany(mappedBy = "cv")
    private Set<Language> languages;

    @OneToMany(mappedBy = "cv")
    private Set<Experience> experienceSet;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<Language> languages) {
        this.languages = languages;
    }

    public Set<Experience> getExperienceSet() {
        return experienceSet;
    }

    public void setExperienceSet(Set<Experience> experienceSet) {
        this.experienceSet = experienceSet;
    }
}
