package com.example.asclepiusjobs.model;

import com.example.asclepiusjobs.model.enums.Profession;
import com.example.asclepiusjobs.model.enums.Salutation;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
public class User {
    @GeneratedValue
    @Id
    private Long id;
    @NotNull
    private boolean active;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @Enumerated
    private Salutation salutation;
    @NotNull
    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;
    @NotNull
    @NotEmpty
    private String password;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;
    @NotNull
    private Date lastActivityTime;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;
    @Enumerated
    private Profession profession;

    @ManyToOne
    private Location location;

    private String phone;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    @PrimaryKeyJoinColumn
    private Cv cv;

    @ManyToOne
    @JoinColumn(name = "health_establishment_id")
    private HealthEstablishment healthEstablishment;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_auth_role",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private Set<JobApplication> jobApplications;

    @ManyToMany
    @JoinTable(name = "user_fav_job_offer",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "job_offer_id"))
    private Set<JobOffer> favouriteJobOffers;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(Date lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public HealthEstablishment getHealthEstablishment() {
        return healthEstablishment;
    }

    public void setHealthEstablishment(HealthEstablishment healthEstablishment) {
        this.healthEstablishment = healthEstablishment;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Cv getCv() {
        return cv;
    }

    public void setCv(Cv cv) {
        this.cv = cv;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications(Set<JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
    }

    public Set<JobOffer> getFavouriteJobOffers() {
        return favouriteJobOffers;
    }

    public void setFavouriteJobOffers(Set<JobOffer> favouriteJobOffers) {
        this.favouriteJobOffers = favouriteJobOffers;
    }
}
