package com.talection.talection.dto;

import com.talection.talection.enums.StudyLevel;

public class AddStudentProfileRequest {
    private int yearOfStudy;
    private Long studyProgramId;

    public void setStudyProgramId(Long studyProgramId) {
        this.studyProgramId = studyProgramId;
    }

    public Long getStudyProgramId() {
        return studyProgramId;
    }

    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }
}
