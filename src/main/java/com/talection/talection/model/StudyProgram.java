package com.talection.talection.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.talection.talection.enums.Campus;
import com.talection.talection.enums.StudyLevel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class StudyProgram {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Campus campus;

    @NotNull
    private StudyLevel studyLevel;

    public StudyProgram() {
        // Default constructor for JPA
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public void setStudyLevel(StudyLevel studyLevel) {
        this.studyLevel = studyLevel;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Campus getCampus() {
        return campus;
    }

    public StudyLevel getStudyLevel() {
        return studyLevel;
    }
}
