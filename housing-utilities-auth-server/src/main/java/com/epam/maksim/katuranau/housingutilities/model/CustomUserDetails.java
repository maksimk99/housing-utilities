package com.epam.maksim.katuranau.housingutilities.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private Integer userId;
    private String userName;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

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
        return userName;
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

    public Integer getUserId() {
        return userId;
    }

    public CustomUserDetails setUserId(final Integer userId) {
        this.userId = userId;
        return this;
    }

    public CustomUserDetails setUserName(final String userName) {
        this.userName = userName;
        return this;
    }

    public CustomUserDetails setPassword(final String password) {
        this.password = password;
        return this;
    }

    public CustomUserDetails setGrantedAuthorities(final Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
        return this;
    }
}
