package com.talection.talection.model.userrelated;

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
    private Long studyProgramId;

    private int yearOfStudy;

    public StudentProfile() {
        // Default constructor for JPA
    }

    /**
     * The field of study for the student (e.g., Computer Science, Mathematics).
     */
    public void setStudyProgramId(Long studyProgramId) {
        this.studyProgramId = studyProgramId;
    }

    /**
     * Gets the ID of the study program for the student.
     *
     * @return the study program ID
     */
    public Long getStudyProgramId() {
        return studyProgramId;
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
