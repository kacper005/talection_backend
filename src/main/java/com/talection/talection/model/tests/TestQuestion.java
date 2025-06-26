package com.talection.talection.model.tests;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

/**
 * Represents a question in a test.
 * This entity stores the question text, options available for the question,
 * and the correct options for the question.
 */
@Entity
public class TestQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String questionText;

    @ManyToMany
    private List<TestOption> options;

    @ManyToMany
    @JsonIgnore
    private List<TestOption> correctOptions;

    /**
     * Default constructor for JPA.
     * This constructor is required for JPA to create instances of this entity.
     */
    public TestQuestion() {
        // Default constructor for JPA
    }

    /**
     * Sets the ID of the question.
     *
     * @param id the ID to set for the question
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the ID of the question.
     *
     * @return the ID of the question
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the text of the question.
     *
     * @param questionText the text to set for the question
     */
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    /**
     * Gets the text of the question.
     *
     * @return the text of the question
     */
    public String getQuestionText() {
        return questionText;
    }

    /**
     * Sets the options available for the question.
     *
     * @param options the list of options to set for the question
     */
    public void setOptions(List<TestOption> options) {
        this.options = options;
    }

    /**
     * Gets the options available for the question.
     *
     * @return the list of options for the question
     */
    public List<TestOption> getOptions() {
        return options;
    }

    /**
     * Sets the correct options for the question.
     *
     * @param correctOptions the list of correct options to set for the question
     */
    public void setCorrectOptions(List<TestOption> correctOptions) {
        this.correctOptions = correctOptions;
    }

    /**
     * Gets the correct options for the question.
     *
     * @return the list of correct options for the question
     */
    public List<TestOption> getCorrectOptions() {
        return correctOptions;
    }
}
