package com.talection.talection.service;

import com.talection.talection.dto.AddStudentProfileRequest;
import com.talection.talection.dto.AddStudyProfileRequest;
import com.talection.talection.exception.StudyProgramNotFoundException;
import com.talection.talection.model.StudyProgram;
import com.talection.talection.repository.StudyProgramRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudyProgramService {
    private final StudyProgramRepository studyProgramRepository;

    public StudyProgramService(StudyProgramRepository studyProgramRepository) {
        this.studyProgramRepository = studyProgramRepository;
    }

    /**
     * Retrieves a study program by its ID.
     *
     * @param id the ID of the study program
     * @return the study program with the specified ID
     * @throws IllegalArgumentException if the ID is null
     */
    public StudyProgram getStudyProgram(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        return studyProgramRepository.findById(id)
                .orElseThrow(() -> new StudyProgramNotFoundException("Study program with ID " + id + " does not exist"));
    }

    /**
     * Adds a new study program to the system.
     *
     * @param request the study program to add
     * @throws IllegalArgumentException if the study program or its fields are null
     */
    public void addStudyProgram(AddStudyProfileRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Study program must not be null");
        }
        if (request.getStudyLevel() == null || request.getCampus() == null || request.getName() == null) {
            throw new IllegalArgumentException("Study level, campus or name must not be null");
        }

        StudyProgram studyProgram = new StudyProgram();
        studyProgram.setName(request.getName());
        studyProgram.setCampus(request.getCampus());
        studyProgram.setStudyLevel(request.getStudyLevel());

        studyProgramRepository.save(studyProgram);
    }

    /**
     * Deletes a study program by its ID.
     *
     * @param id the ID of the study program to delete
     * @throws IllegalArgumentException if the ID is null
     * @throws StudyProgramNotFoundException if the study program with the specified ID does not exist
     */
    public void deleteStudyProgram(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        if (!studyProgramRepository.existsById(id)) {
            throw new StudyProgramNotFoundException("Study program with ID " + id + " does not exist");
        }
        studyProgramRepository.deleteById(id);
    }

    /**
     * Retrieves all study programs.
     *
     * @return a collection of all study programs
     */
    public Collection<StudyProgram> getAllStudyPrograms() {
        return studyProgramRepository.findAll();
    }
}
