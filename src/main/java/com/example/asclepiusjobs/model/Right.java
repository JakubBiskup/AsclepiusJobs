package com.example.asclepiusjobs.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "auth_right")
public class Right {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @NotEmpty
    @Column(name = "name",nullable = false)
    private String name;

    @ManyToMany(mappedBy = "rights")
    private Set<Role> roles;

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

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
