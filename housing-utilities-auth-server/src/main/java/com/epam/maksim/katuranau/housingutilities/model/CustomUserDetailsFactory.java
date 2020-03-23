package com.epam.maksim.katuranau.housingutilities.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

public class CustomUserDetailsFactory {
    public static CustomUserDetails createCustomUserDetails(final UserInfo userInfo) {
        Collection<? extends GrantedAuthority> grantedAuthorities = userInfo.getUserRoles().stream().map(userRole ->
                new SimpleGrantedAuthority(userRole.getRole())).collect(Collectors.toList());
        return new CustomUserDetails()
                .setUserId(userInfo.getUserId())
                .setUserName(userInfo.getUserName())
                .setPassword(userInfo.getPassword())
                .setGrantedAuthorities(grantedAuthorities);
    }
}
