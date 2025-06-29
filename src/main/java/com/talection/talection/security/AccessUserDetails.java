package com.talection.talection.security;

import com.talection.talection.model.userrelated.User;
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
    private Long id;
    private String email;
    private String password;
    private GrantedAuthority authority;

    /**
     * Constructs an AccessUserDetails object from a User entity.
     *
     * @param user the User entity containing user information
     */
    public AccessUserDetails(User user) {
        this.id = user.getId();
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

    /**
     * Returns the users id
     *
     * @return the id of the user
     */
    public Long getId() {
        return id;
    }
}
