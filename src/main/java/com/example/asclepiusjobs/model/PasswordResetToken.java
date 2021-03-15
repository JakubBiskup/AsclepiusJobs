package com.example.asclepiusjobs.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;

@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @NotEmpty
    private String token;

    @OneToOne
    @JoinColumn(nullable = false, name="user_id",unique = true)
    private User user;

    private Date expirationDate;

    private Date calculateExpirationDate(int expirationTimeInMinutes){
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MINUTE,expirationTimeInMinutes);
        return calendar.getTime();
    }

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expirationDate=calculateExpirationDate(60*24);

    }

    public PasswordResetToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
