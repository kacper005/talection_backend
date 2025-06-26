package com.talection.talection.model.testpersistance;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a choice made by a user in a test.
 */
@Entity
public class TestChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long questionId;

    @NotNull
    private Long selectedOptionId;

    /**
     * Default constructor for JPA.
     * This constructor is required for JPA to create instances of this entity.
     */
    public TestChoice() {
        // Default constructor for JPA
    }

    /**
     * Sets the ID of the question associated with this choice.
     *
     * @param questionId the ID of the question to set
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    /**
     * Gets the ID of the question associated with this choice.
     *
     * @return the ID of the question
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * Sets the ID of the selected option for this choice.
     *
     * @param optionId the ID of the selected option to set
     */
    public void setSelectedOptionId(Long optionId) {
        this.selectedOptionId = optionId;
    }

    /**
     * Gets the ID of the selected option for this choice.
     *
     * @return the ID of the selected option
     */
    public Long getSelectedOptionId() {
        return selectedOptionId;
    }

    /**
     * Sets the ID of this choice.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the ID of this choice.
     *
     * @return the ID of this choice
     */
    public Long getId() {
        return id;
    }
}
