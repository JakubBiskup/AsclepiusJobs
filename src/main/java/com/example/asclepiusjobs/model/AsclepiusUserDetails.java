package com.example.asclepiusjobs.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class AsclepiusUserDetails implements UserDetails {

    private String username;
    private String password;
    private boolean active;
    private List<GrantedAuthority> grantedAuthorities;
    public AsclepiusUserDetails(User user){
        this.username=user.getEmail();
        this.password=user.getPassword();
        this.active=user.isActive();
        List<GrantedAuthority> authorityList=new ArrayList<>();
        for(Role role:user.getRoles()){
            for(Right right:role.getRights()){
                SimpleGrantedAuthority authority=new SimpleGrantedAuthority(right.getName());
                authorityList.add(authority);
            }

        }
        this.grantedAuthorities = authorityList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    //the following 3 methods always return true for now, change it later

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active; //
    }
}
