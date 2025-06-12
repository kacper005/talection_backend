package com.talection.talection.dto;

import com.talection.talection.enums.Gender;
import jakarta.validation.constraints.NotNull;

public class UpdateUserRequest {
    private String firstName;
    private String lastName;
    private Gender gender;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Gender getGender() {
        return this.gender;
    }
}
