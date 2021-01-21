package com.example.asclepiusjobs.model;

import javax.persistence.*;
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
    @NotNull
    @NotEmpty
    private Date creationDate;
    @NotNull
    @NotEmpty
    private String name;
    @ManyToOne
    private Location location;

    //other contact information

    //billing

    @OneToMany(mappedBy = "healthEstablishment")
    private Set<User> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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
}
