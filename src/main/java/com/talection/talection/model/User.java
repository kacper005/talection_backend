package com.talection.talection.model;

import com.talection.talection.enums.Gender;
import com.talection.talection.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.Date;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String googleId;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    private String profilePictureUrl;

    private Role role;

    private Gender gender;

    private Date createdAt;

    public User() {
        // Default constructor for JPA
    }

    public String getEmail() {
        return email;
    }

    public Long getId() {
        return id;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public Role getRole() {
        return role;
    }

    public Gender getGender() {
        return gender;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
