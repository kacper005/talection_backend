package com.talection.talection.repository;

import com.talection.talection.enums.Campus;
import com.talection.talection.enums.StudyLevel;
import com.talection.talection.model.StudyProgram;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.function.Function;

public interface StudyProgramRepository extends JpaRepository<StudyProgram, Long> {
    public StudyProgram findByStudyLevelAndCampus(StudyLevel studyLevel, Campus campus);

    public StudyProgram findByStudyLevel(StudyLevel studyLevel);
}
