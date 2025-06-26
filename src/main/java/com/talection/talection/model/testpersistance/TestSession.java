package com.talection.talection.model.testpersistance;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

/**
 * Represents a test session for a user.
 * This entity stores information about the user's test session,
 * including the user ID, test template ID, start and end times,
 * and the choices made by the user during the test.
 */
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

    /**
     * Default constructor for JPA.
     * This constructor is required for JPA to create instances of this entity.
     */
    public TestSession() {
        // Default constructor for JPA
    }

    /**
     * Sets the ID of the test session.
     *
     * @param id the ID to set for the test session
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the ID of the test session.
     *
     * @return the ID of the test session
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the user ID associated with this test session.
     *
     * @param userId the ID of the user to set
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the user ID associated with this test session.
     *
     * @return the ID of the user
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the test template ID associated with this test session.
     *
     * @param testTemplateId the ID of the test template to set
     */
    public void setTestTemplateId(Long testTemplateId) {
        this.testTemplateId = testTemplateId;
    }

    /**
     * Gets the test template ID associated with this test session.
     *
     * @return the ID of the test template
     */
    public Long getTestTemplateId() {
        return testTemplateId;
    }

    /**
     * Sets the start time of the test session.
     *
     * @param startTime the start time to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the start time of the test session.
     *
     * @return the start time of the test session
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the end time of the test session.
     *
     * @param endTime the end time to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the end time of the test session.
     *
     * @return the end time of the test session
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the choices made by the user during the test session.
     *
     * @param choices the list of choices to set
     */
    public void setChoices(List<TestChoice> choices) {
        this.choices = choices;
    }

    /**
     * Gets the choices made by the user during the test session.
     *
     * @return the list of choices made by the user
     */
    public List<TestChoice> getChoices() {
        return choices;
    }
}
