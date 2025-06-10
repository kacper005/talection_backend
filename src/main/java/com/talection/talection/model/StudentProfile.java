package com.talection.talection.model;

import com.talection.talection.enums.StudyLevel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a student's profile in the system.
 * Contains information about the student's study level, field of study, and year of study.
 */
@Entity
public class StudentProfile {
    @Id
    private Long id;

    @NotNull
    private StudyLevel studyLevel;

    @NotBlank
    private String fieldOfStudy;

    private int yearOfStudy;

    public StudentProfile() {
        // Default constructor for JPA
    }

    /**
     * Sets the study level of the student.
     *
     * @param studyLevel the level of study (e.g., BACHELOR, MASTER, PHD)
     */
    public void setStudyLevel(StudyLevel studyLevel) {
        this.studyLevel = studyLevel;
    }

    /**
     * Gets the study level of the student.
     *
     * @return the level of study
     */
    public StudyLevel getStudyLevel() {
        return studyLevel;
    }

    /**
     * Sets the ID of the student profile.
     *
     * @param id the users id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the ID of the student profile.
     *
     * @return the user's id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the field of study for the student.
     *
     * @param fieldOfStudy the field of study (e.g., Computer Science, Mathematics)
     */
    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    /**
     * Gets the field of study for the student.
     *
     * @return the field of study
     */
    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    /**
     * Sets the year of study for the student.
     *
     * @param yearOfStudy the year of study (e.g., 1, 2, 3, etc.)
     */
    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    /**
     * Gets the year of study for the student.
     *
     * @return the year of study
     */
    public int getYearOfStudy() {
        return yearOfStudy;
    }
}
