package com.talection.talection.dto;

import com.talection.talection.enums.StudyLevel;

public class AddStudentProfileRequest {
    private StudyLevel studyLevel;
    private String fieldOfStudy;
    private int yearOfStudy;

    public void setStudyLevel(StudyLevel studyLevel) {
        this.studyLevel = studyLevel;
    }

    public StudyLevel getStudyLevel() {
        return studyLevel;
    }

    public void setFieldOfStudy(String fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }
}
