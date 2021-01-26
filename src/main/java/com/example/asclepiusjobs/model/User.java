package com.example.asclepiusjobs.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class User {
    @GeneratedValue
    @Id
    private Long id;
    @NotNull
    @NotEmpty
    private String firstName;
    @NotNull
    @NotEmpty
    private String lastName;
    @NotNull
    @NotEmpty
    @Email
    private String email;
    @NotNull
    @NotEmpty
    private String password;
    @NotNull
    @NotEmpty
    @Column(unique = true)
    private String identifying;
    @NotNull
    private Date creationDate;
    @NotNull
    private Date connectionDate;
    @NotNull
    @NotEmpty
    private String role;
    private Boolean isFemale;
    private String profession;

    @ManyToOne
    private Location location;

    private String mail;

    private String phone;
    private Long cv_id;            //
   // private Set<SearchProfile> searchProfiles; //

  //  private Set<Right> rights; //
    @ManyToOne
    @JoinColumn(name = "health_establishment_id")
    private HealthEstablishment healthEstablishment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIdentifying() {
        return identifying;
    }

    public void setIdentifying(String identifying) {
        this.identifying = identifying;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getConnectionDate() {
        return connectionDate;
    }

    public void setConnectionDate(Date connectionDate) {
        this.connectionDate = connectionDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getFemale() {
        return isFemale;
    }

    public void setFemale(Boolean female) {
        isFemale = female;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getCv_id() {
        return cv_id;
    }

    public void setCv_id(Long cv_id) {
        this.cv_id = cv_id;
    }

    public HealthEstablishment getHealthEstablishment() {
        return healthEstablishment;
    }

    public void setHealthEstablishment(HealthEstablishment healthEstablishment) {
        this.healthEstablishment = healthEstablishment;
    }
}
