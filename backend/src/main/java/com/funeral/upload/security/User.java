package com.funeral.upload.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author FuneralObjects
 * CreateTime 2018/11/17 1:41 PM
 */
public class User implements UserDetails {
    private String username;
    private String password;
    private Boolean enable;
    private Boolean expired;

    public User(String username, String password, Boolean enable, Boolean expired) {
        this.username = username;
        this.password = password;
        this.enable = enable;
        this.expired = expired;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
        return expired;
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
        return enable;
    }
}
