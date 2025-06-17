package com.talection.talection.model.testpersistance;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class TestChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long questionId;

    @NotNull
    private Long selectedOptionId;


    public TestChoice() {
        // Default constructor for JPA
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setSelectedOptionId(Long optionId) {
        this.selectedOptionId = optionId;
    }

    public Long getSelectedOptionId() {
        return selectedOptionId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
