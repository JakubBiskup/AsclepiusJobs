package com.example.asclepiusjobs.model;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "HEALTH_ESTABLISHMENT")
public class HealthEstablishment {
    @GeneratedValue
    @Id
    private Long id;

    @NotEmpty
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "logo_path")
    private String logoPath;

    @Column(name = "description",length = 2000)
    private String description;

    @Column(name = "phone")
    private String phone;

    @Column(name = "website")
    private String website;

    @Email
    @Column(name = "contact_email")
    private String contactEmail;

    @NotNull
    @NotEmpty
    private Date createTime;

    private Date updateTime;

    @ManyToOne
    private Location location;

    //billing

    @OneToMany(mappedBy = "healthEstablishment")
    private Set<User> users;

    @OneToMany(mappedBy = "healthEstablishment")
    private Set<JobOffer> jobOffers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Set<JobOffer> getJobOffers() {
        return jobOffers;
    }

    public void setJobOffers(Set<JobOffer> jobOffers) {
        this.jobOffers = jobOffers;
    }
}
