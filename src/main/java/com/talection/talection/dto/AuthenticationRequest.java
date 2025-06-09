package com.talection.talection.dto;

import com.talection.talection.enums.AuthProvider;

/**
 * DTO for authentication requests.
 * This class is used to encapsulate the data required for user authentication.
 * It supports multiple authentication providers such as Google, Feide, and local authentication.
 */
public class AuthenticationRequest {

    private AuthProvider authProvider;

    // Optional, used for Google and Feide authentication
    private String credentialId;

    // Optional, used for local authentication
    private String email;

    // Optional, used for local authentication
    private String password;

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }
}
