package com.talection.talection.model.tests;

import com.talection.talection.exception.LikertScaleOutOfBoundsException;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class LikertScaleOption implements TestOption {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;


    private int agreementLevel; // Represents the level of agreement, e.g., 1 to 5


    public LikertScaleOption() {
        // Default constructor for JPA
    }

    @Override
    public Long getId() {
        return null;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAgreementLevel() {
        return agreementLevel;
    }

    public void setAgreementLevel(int agreementLevel) {
        if (agreementLevel < 1 || agreementLevel > 5) {
            throw new LikertScaleOutOfBoundsException("Agreement level must be between 1 and 5");
        }
    }
}
