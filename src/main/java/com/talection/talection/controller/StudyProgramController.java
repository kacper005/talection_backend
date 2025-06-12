package com.talection.talection.controller;

import com.talection.talection.dto.AddStudyProgramRequest;
import com.talection.talection.exception.StudyProgramNotFoundException;
import com.talection.talection.model.StudyProgram;
import com.talection.talection.service.StudyProgramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * Controller for managing study programs.
 * Provides endpoints to retrieve, add, and delete study programs.
 */
@RestController
@RequestMapping("/study-program")
public class StudyProgramController {
    private final StudyProgramService studyProgramService;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);


    public StudyProgramController(StudyProgramService studyProgramService) {
        this.studyProgramService = studyProgramService;
    }

    /**
     * Retrieves all study programs.
     *
     * @return ResponseEntity containing a collection of study programs or an error status
     */
    @GetMapping()
    @PreAuthorize("hasAnyAuthority('STUDENT', 'TEACHER', 'ADMIN')")
    public ResponseEntity<Collection<StudyProgram>> getAllStudyPrograms() {
        try {
            Collection<StudyProgram> studyPrograms = studyProgramService.getAllStudyPrograms();
            logger.info("Retrieved all study programs successfully");
            return ResponseEntity.ok(studyPrograms);
        } catch (Exception e) {
            logger.error("Error retrieving study programs: {}", e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Add a new study program. This endpoint is restricted to users with ADMIN authority.
     *
     * @param addStudyProfileRequest the request containing study program details
     * @return ResponseEntity indicating success or failure
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> addStudyProgram(@RequestBody AddStudyProgramRequest addStudyProfileRequest) {
        if (addStudyProfileRequest == null) {
            return ResponseEntity.badRequest().body("Study program request cannot be null");
        }

        try {
            studyProgramService.addStudyProgram(addStudyProfileRequest);
            logger.info("Study program added successfully: {}", addStudyProfileRequest.getName());
            return ResponseEntity.ok("Study program added successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Error adding study program: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Updates an existing study program by its ID. This endpoint is restricted to users with ADMIN authority.
     *
     * @param id the ID of the study program to update
     * @param addStudyProfileRequest the request containing updated study program details
     * @return ResponseEntity indicating success or failure
     */
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> updateStudyProgram(
            @PathVariable(value = "id", required = true) Long id,
            @RequestBody AddStudyProgramRequest addStudyProfileRequest) {
        if (id == null || addStudyProfileRequest == null) {
            return ResponseEntity.badRequest().body("ID and study program request must not be null");
        }

        try {
            studyProgramService.updateStudyProgram(addStudyProfileRequest, id);
            logger.info("Study program updated successfully with ID: {}", id);
            return ResponseEntity.ok("Study program updated successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Error updating study program: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (StudyProgramNotFoundException e) {
            logger.error("Study program not found: {}", e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    /**
     * Deletes a study program by its ID. This endpoint is restricted to users with ADMIN authority.
     *
     * @param id the ID of the study program to delete
     * @return ResponseEntity indicating success or failure
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> deleteStudyProgram(
            @PathVariable(value = "id", required = true) Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("ID must not be null");
        }

        try {
            studyProgramService.deleteStudyProgram(id);
            logger.info("Study program deleted successfully with ID: {}", id);
            return ResponseEntity.ok("Study program deleted successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Error deleting study program: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (StudyProgramNotFoundException e) {
            logger.error("Study program not found: {}", e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
