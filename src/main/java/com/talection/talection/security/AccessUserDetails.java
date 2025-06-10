package com.talection.talection.security;

import com.talection.talection.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * AccessUserDetails is a custom implementation of UserDetails that provides user information
 * for authentication purposes.
 */
public class AccessUserDetails implements UserDetails {
    private String email;
    private String password;
    private GrantedAuthority authority;

    public AccessUserDetails(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authority = new SimpleGrantedAuthority(user.getRole().name());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
