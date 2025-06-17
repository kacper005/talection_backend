package com.talection.talection.model.tests;

import com.talection.talection.enums.TestOptionType;
import com.talection.talection.enums.TestType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Set;

@Entity
public class TestTemplate {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @Column(unique = true)
    private TestType testType;

    @NotNull
    private TestOptionType optionType;

    @ManyToMany
    private List<TestQuestion> questions;

    public TestTemplate() {
        // Default constructor for JPA
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setTestType(TestType testType) {
        this.testType = testType;
    }

    public TestType getTestType() {
        return testType;
    }

    public void setQuestions(List<TestQuestion> questions) {
        this.questions = questions;
    }

    public List<TestQuestion> getQuestions() {
        return questions;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setOptionType(TestOptionType optionType) {
        this.optionType = optionType;
    }

    public TestOptionType getOptionType() {
        return optionType;
    }
}
