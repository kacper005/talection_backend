package com.talection.talection.dto;

import com.talection.talection.enums.Campus;
import com.talection.talection.enums.StudyLevel;

public class AddStudyProfileRequest {
    private String name;
    private Campus campus;
    private StudyLevel studyLevel;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setStudyLevel(StudyLevel studyLevel) {
        this.studyLevel = studyLevel;
    }

    public StudyLevel getStudyLevel() {
        return studyLevel;
    }
}
