package com.talection.talection.model.tests;

import com.talection.talection.enums.TestOptionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class TestOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private TestOptionType type;

    private String optionText;

    private int agreementLevel;

    public TestOption() {
        // Default constructor for JPA
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TestOptionType getType() {
        return type;
    }

    public void setType(TestOptionType type) {
        this.type = type;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public int getAgreementLevel() {
        return agreementLevel;
    }

    public void setAgreementLevel(int agreementLevel) {
        this.agreementLevel = agreementLevel;
    }
}