package com.talection.talection.repository.userrelated;

import com.talection.talection.enums.Campus;
import com.talection.talection.enums.StudyLevel;
import com.talection.talection.model.userrelated.StudyProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyProgramRepository extends JpaRepository<StudyProgram, Long> {
    public StudyProgram findByStudyLevelAndCampus(StudyLevel studyLevel, Campus campus);

    public StudyProgram findByStudyLevel(StudyLevel studyLevel);
}
