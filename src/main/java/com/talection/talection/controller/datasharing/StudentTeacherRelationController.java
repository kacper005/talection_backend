package com.talection.talection.controller.datasharing;

import com.talection.talection.dto.replies.TeacherRelationReply;
import com.talection.talection.dto.replies.TestSessionReply;
import com.talection.talection.dto.requests.AddStudentTeacherRelationRequest;
import com.talection.talection.security.AccessUserDetails;
import com.talection.talection.service.datasharing.StudentTeacherRelationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/student-teacher-relation")
public class StudentTeacherRelationController {
    private final StudentTeacherRelationService studentTeacherRelationService;

    private final Logger logger = LoggerFactory.getLogger(StudentTeacherRelationController.class);

    public StudentTeacherRelationController(StudentTeacherRelationService studentTeacherRelationService) {
        this.studentTeacherRelationService = studentTeacherRelationService;
    }

    /**
     * Adds a new student-teacher relation for the currently authenticated student.
     *
     * @param request the request containing teacher ID and test session ID
     * @return ResponseEntity containing the ID of the created relation or an error message
     */
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<String> addStudentTeacherRelation(@RequestBody AddStudentTeacherRelationRequest request) {
        AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (request == null || request.getTeacherId() == null || request.getTestSessionId() == null) {
            logger.error("Invalid request: {}", request);
            return ResponseEntity.badRequest().body("Invalid request");
        }

        try {
            Long id = studentTeacherRelationService.addStudentTeacherRelation(userDetails.getId(), request.getTeacherId(), request.getTestSessionId());
            logger.info("Successfully added student-teacher relation with ID: {}", id);
            return ResponseEntity.ok(id.toString());
        } catch (IllegalArgumentException e) {
            logger.error("Error adding student-teacher relation: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while adding student-teacher relation: {}", e.getMessage());
            return ResponseEntity.status(500).body("An unexpected error occurred");
        }
    }

    @GetMapping("/teachers")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<Collection<TeacherRelationReply>> getTeachersForStudent() {
        AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            Collection<TeacherRelationReply> relations = studentTeacherRelationService.getTeachersByStudentId(userDetails.getId());
            logger.info("Successfully retrieved teachers for student with ID: {}", userDetails.getId());
            return ResponseEntity.ok(relations);
        } catch (IllegalArgumentException e) {
            logger.error("Error retrieving teachers for student with ID {}: {}", userDetails.getId(), e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving teachers for student with ID {}: {}", userDetails.getId(), e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Retrieves all test sessions for the currently authenticated teacher.
     *
     * @return ResponseEntity containing a collection of TestSessionReply objects
     */
    @GetMapping("/testSessions")
    @PreAuthorize("hasAnyAuthority('TEACHER', 'STUDENT')")
    public ResponseEntity<Collection<TestSessionReply>> getTestSessionsForUsers() {
        AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            Collection<TestSessionReply> testSessionReply = studentTeacherRelationService.getTestSessionsByTeacherIdOrStudentId(userDetails.getId());
            logger.info("Successfully retrieved test sessions for user with ID: {}", userDetails.getId());
            return ResponseEntity.ok(testSessionReply);
        } catch (IllegalArgumentException e) {
            logger.error("Error retrieving test sessions for user with ID {}: {}", userDetails.getId(), e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Unexpected error while retrieving test sessions for user with ID {}: {}", userDetails.getId(), e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

    /**
     * Deletes a student-teacher relation by its ID.
     *
     * @param id the ID of the student-teacher relation to delete
     * @return ResponseEntity indicating success or failure
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public ResponseEntity<String> deleteStudentTeacherRelation(@PathVariable Long id) {
        AccessUserDetails userDetails = (AccessUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (id == null) {
            logger.error("Invalid ID provided for deletion: {}", id);
            return ResponseEntity.badRequest().body("Invalid ID");
        }

        try {
            studentTeacherRelationService.deleteStudentTeacherRelation(userDetails.getId(), id);
            logger.info("Successfully deleted student-teacher relation with ID: {}", id);
            return ResponseEntity.ok("Student-teacher relation deleted successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Error deleting student-teacher relation with ID {}: {}", id, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error while deleting student-teacher relation with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(500).body("An unexpected error occurred");
        }
    }
}
