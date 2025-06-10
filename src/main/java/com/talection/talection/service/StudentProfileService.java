package com.talection.talection.service;

import com.talection.talection.dto.AddStudentProfileRequest;
import com.talection.talection.exception.UserAlreadyExistsException;
import com.talection.talection.exception.UserNotFoundException;
import com.talection.talection.model.StudentProfile;
import com.talection.talection.repository.StudentProfileRepository;
import org.springframework.stereotype.Service;

/**
 * Service class for managing student profiles.
 * Provides methods to add and manage student profiles.
 */
@Service
public class StudentProfileService {
    private final StudentProfileRepository studentProfileRepository;

    public StudentProfileService(StudentProfileRepository studentProfileRepository) {
        this.studentProfileRepository = studentProfileRepository;
    }

    /**
     * Retrieves a student profile by its ID.
     *
     * @param id the ID of the student profile
     * @return the student profile with the specified ID
     * @throws IllegalArgumentException if the ID is null
     */
    public StudentProfile getStudentProfile(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        return studentProfileRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Student profile with ID " + id + " does not exist"));
    }

    /**
     * Adds a new student profile to the system.
     *
     * @param request the student profile request to add
     * @param userId the ID of the user adding the student profile
     * @throws IllegalArgumentException if the student profile or its ID is null
     */
    public void addStudentProfile(AddStudentProfileRequest request, Long userId) {
        if (request == null) {
            throw new IllegalArgumentException("Student profile and user must not be null");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }

        StudentProfile studentProfile = new StudentProfile();
        studentProfile.setId(userId);
        studentProfile.setStudyLevel(request.getStudyLevel());
        studentProfile.setFieldOfStudy(request.getFieldOfStudy());
        studentProfile.setYearOfStudy(request.getYearOfStudy());
        studentProfileRepository.save(studentProfile);
    }

    /**
     * Retrieves a student profile by its ID.
     *
     * @param id the ID of the student profile
     * @return the student profile with the specified ID
     * @throws IllegalArgumentException if the ID is null
     */
    public void removeStudentProfile(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        studentProfileRepository.deleteById(id);
    }

    /**
     * Checks if a student profile exists by its ID.
     *
     * @param id the ID of the student profile
     * @return true if the student profile exists, false otherwise
     * @throws IllegalArgumentException if the ID is null
     */
    public boolean hasStudentProfile(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }
        return studentProfileRepository.existsById(id);
    }
}
