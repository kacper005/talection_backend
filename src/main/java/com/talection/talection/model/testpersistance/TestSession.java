package com.talection.talection.model.testpersistance;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

@Entity
public class TestSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long testTemplateId;

    @NotNull
    private Date startTime;

    @NotNull
    private Date endTime;

    @NotNull
    @ManyToMany
    private List<TestChoice> choices;

    public TestSession() {
        // Default constructor for JPA
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setTestTemplateId(Long testTemplateId) {
        this.testTemplateId = testTemplateId;
    }

    public Long getTestTemplateId() {
        return testTemplateId;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setChoices(List<TestChoice> choices) {
        this.choices = choices;
    }

    public List<TestChoice> getChoices() {
        return choices;
    }
}
