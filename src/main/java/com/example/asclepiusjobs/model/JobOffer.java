package com.example.asclepiusjobs.model;

import com.example.asclepiusjobs.model.enums.Profession;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.Date;
import java.util.Set;

@Entity
public class JobOffer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "mission_start_date", nullable = false)
    private Date missionStartDate;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "profession", nullable = false)
    private Profession profession;

    @Column(name = "duration")
    private Integer duration;

    @Column(name = "salary")
    private Integer salary;

    @Column(name = "mission")
    private String mission;

    @Column(name = "description", length = 2000)
    private String description;

    @Column(name = "experience_required")
    private Integer experienceRequired;

    @Column(name = "visibility", nullable = false)
    private boolean visibility;

    @Email
    @Column(name = "email")
    private String email;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    @ManyToOne
    private Location location;

    @ManyToMany
    @JoinTable(name = "job_offer_skill",
    joinColumns = @JoinColumn(name = "job_offer_id"),
    inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private Set<Skill> skillsRequired;


    @ManyToOne
    @JoinColumn(name = "health_establishment_id")
    private HealthEstablishment healthEstablishment;

    @OneToMany(mappedBy = "jobOffer")
    private Set<JobApplication> jobApplications;

    @ManyToMany(mappedBy = "favouriteJobOffers")
    private Set<User> usersFav;


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

    public Date getMissionStartDate() {
        return missionStartDate;
    }

    public void setMissionStartDate(Date missionStartDate) {
        this.missionStartDate = missionStartDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getExperienceRequired() {
        return experienceRequired;
    }

    public void setExperienceRequired(Integer experienceRequired) {
        this.experienceRequired = experienceRequired;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public HealthEstablishment getHealthEstablishment() {
        return healthEstablishment;
    }

    public void setHealthEstablishment(HealthEstablishment healthEstablishment) {
        this.healthEstablishment = healthEstablishment;
    }

    public Set<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications(Set<JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
    }

    public Set<Skill> getSkillsRequired() {
        return skillsRequired;
    }

    public void setSkillsRequired(Set<Skill> skillsRequired) {
        this.skillsRequired = skillsRequired;
    }

    public Set<User> getUsersFav() {
        return usersFav;
    }

    public void setUsersFav(Set<User> usersFav) {
        this.usersFav = usersFav;
    }
}
