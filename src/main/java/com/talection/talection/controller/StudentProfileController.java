package com.talection.talection.controller;

import com.talection.talection.dto.AddStudentProfileRequest;
import com.talection.talection.exception.StudyProgramNotFoundException;
import com.talection.talection.exception.UserAlreadyExistsException;
import com.talection.talection.exception.UserNotFoundException;
import com.talection.talection.model.StudentProfile;
import com.talection.talection.model.User;
import com.talection.talection.security.AccessUserDetails;
import com.talection.talection.service.StudentProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing student profiles.
 * Provides endpoints to retrieve and add student profiles for the currently authenticated user.
 */
@RestController
@RequestMapping("/student-profile")
public class StudentProfileController {
    private final StudentProfileService studentProfileService;

    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    /**
     * Retrieves the student profile for the currently authenticated user.
     *
     * @return ResponseEntity containing the student profile or an error status
     */
    @GetMapping
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<StudentProfile> getStudentProfileForCurrentAuthenticatedUser() {
        AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            StudentProfile studentProfile = studentProfileService.getStudentProfile(userDetails.getId());
            return ResponseEntity.ok(studentProfile);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Adds a student profile for the currently authenticated user.
     *
     * @param request the request containing student profile details
     * @return ResponseEntity indicating success or failure
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<String> addStudentProfileToCurrentAuthenticatedUser(@RequestBody AddStudentProfileRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().body("Student profile request cannot be null");
        }
        if (request.getStudyProgramId() == null) {
            return ResponseEntity.badRequest().body("Study program ID");
        }

        AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            studentProfileService.addStudentProfile(request, userDetails.getId());
            return ResponseEntity.ok("Student profile added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid student profile request");
        } catch (StudyProgramNotFoundException e) {
            return ResponseEntity.badRequest().body("Study program with the given ID does not exist");
        }
    }

    /**
     * Updates the student profile for the currently authenticated user.
     *
     * @param request the request containing updated student profile details
     * @return ResponseEntity indicating success or failure
     */
    @PutMapping("/update")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<String> updateStudentProfileForCurrentAuthenticatedUser(@RequestBody AddStudentProfileRequest request) {
        if (request == null) {
            return ResponseEntity.badRequest().body("Student profile request cannot be null");
        }
        AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            studentProfileService.updateStudentProfile(request, userDetails.getId());
            return ResponseEntity.ok("Student profile updated successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid student profile request");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student profile not found");
        }
    }

    /**
     * Removes the student profile for the currently authenticated user.
     *
     * @return ResponseEntity indicating success or failure
     */
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<String> removeStudentProfileForCurrentAuthenticatedUser() {
        AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            studentProfileService.removeStudentProfile(userDetails.getId());
            return ResponseEntity.ok("Student profile removed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid student profile ID");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student profile not found");
        }
    }
}
