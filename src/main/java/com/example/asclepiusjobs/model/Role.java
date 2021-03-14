package com.example.asclepiusjobs.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "auth_role")
public class Role {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @ManyToMany(mappedBy = "roles")
    Set<User> users;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "auth_role_auth_right",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "right_id"))
    private Set<Right> rights;

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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Right> getRights() {
        return rights;
    }

    public void setRights(Set<Right> rights) {
        this.rights = rights;
    }
}
