package com.comrade.reactjsbackend.model;

import com.comrade.reactjsbackend.entity.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;
import java.util.stream.Collectors;


public class AuthUser implements UserDetails {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    Collection<? extends GrantedAuthority> authorities;

    public AuthUser(UserEntity userEntity){
        this.username = userEntity.getUserName();
        this.password = userEntity.getPassword();
        this.firstName = userEntity.getFirstName();
        this.lastName = userEntity.getLastName();
        this.email = userEntity.getEmail();
        this.authorities = userEntity.getRoles().stream()
                                       .map(roleEntity -> new SimpleGrantedAuthority(roleEntity.getName().toUpperCase()))
                                       .collect(Collectors.toList());
    }


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

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
        return true;
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }
}
