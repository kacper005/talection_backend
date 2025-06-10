package com.talection.talection.security;

import com.talection.talection.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * AccessUserDetails is a custom implementation of UserDetails that provides user information
 * for authentication purposes.
 */
public class AccessUserDetails implements UserDetails {
    private String email;
    private String password;

    public AccessUserDetails(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
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
