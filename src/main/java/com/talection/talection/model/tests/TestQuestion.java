package com.talection.talection.model.tests;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

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
    private List<TestOption> correctOptions;

    public TestQuestion() {
        // Default constructor for JPA
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setOptions(List<TestOption> options) {
        this.options = options;
    }

    public List<TestOption> getOptions() {
        return options;
    }

    public void setCorrectOptions(List<TestOption> correctOptions) {
        this.correctOptions = correctOptions;
    }

    public List<TestOption> getCorrectOptions() {
        return correctOptions;
    }
}
