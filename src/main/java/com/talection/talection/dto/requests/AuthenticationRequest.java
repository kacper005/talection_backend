package com.talection.talection.dto.requests;

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

    /**
     * Gets the credential ID. This is used for Google and Feide authentication.
     *
     * @return the credential ID, which is used for Google and Feide authentication.
     */
    public String getCredentialId() {
        return credentialId;
    }

    /**
     * Sets the credential ID. This is used for Google and Feide authentication.
     *
     * @param credentialId the credential ID to set, which is used for Google and Feide authentication.
     */
    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    /**
     * Gets the email. This is used for local authentication.
     *
     * @return the email, which is used for local authentication.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email. This is used for local authentication.
     *
     * @param email the email to set, which is used for local authentication.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password. This is used for local authentication.
     *
     * @return the password, which is used for local authentication.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password. This is used for local authentication.
     *
     * @param password the password to set, which is used for local authentication.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the authentication provider.
     * This indicates which authentication method is being used (e.g., Google, Feide, or local).
     *
     * @return the authentication provider.
     */
    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    /**
     * Sets the authentication provider.
     * This indicates which authentication method is being used (e.g., Google, Feide, or local).
     *
     * @param authProvider the authentication provider to set.
     */
    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }
}
