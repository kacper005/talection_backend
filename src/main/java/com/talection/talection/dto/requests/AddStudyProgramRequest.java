package com.talection.talection.dto.requests;

import com.talection.talection.enums.Campus;
import com.talection.talection.enums.StudyLevel;

/**
 * DTO for adding a study program.
 */
public class AddStudyProgramRequest {
    private String name;
    private Campus campus;
    private StudyLevel studyLevel;

    /**
     * Sets the name of the study program.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the study program.
     *
     * @return the name of the study program
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the campus of the study program.
     *
     * @param campus the campus to set
     */
    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    /**
     * Gets the campus of the study program.
     *
     * @return the campus of the study program
     */
    public Campus getCampus() {
        return campus;
    }

    /**
     * Sets the study level of the study program.
     *
     * @param studyLevel the study level to set
     */
    public void setStudyLevel(StudyLevel studyLevel) {
        this.studyLevel = studyLevel;
    }

    /**
     * Gets the study level of the study program.
     *
     * @return the study level of the study program
     */
    public StudyLevel getStudyLevel() {
        return studyLevel;
    }
}
