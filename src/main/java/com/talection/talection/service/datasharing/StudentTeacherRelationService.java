package com.talection.talection.service.datasharing;

import com.talection.talection.dto.replies.TestSessionReply;
import com.talection.talection.enums.Role;
import com.talection.talection.model.datasharing.StudentTeacherRelation;
import com.talection.talection.repository.datasharing.StudentTeacherRelationRepository;
import com.talection.talection.service.testpersistance.TestSessionService;
import com.talection.talection.service.userrelated.UserService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

@Service
public class StudentTeacherRelationService {
    private final StudentTeacherRelationRepository studentTeacherRelationRepository;
    private final TestSessionService testSessionService;
    private final UserService userService;

    public StudentTeacherRelationService(StudentTeacherRelationRepository studentTeacherRelationRepository,
                                         TestSessionService testSessionService, UserService userService) {
        this.studentTeacherRelationRepository = studentTeacherRelationRepository;
        this.testSessionService = testSessionService;
        this.userService = userService;
    }

    /**
     * Adds a new Student-Teacher relation for a given student, teacher, and test session.
     *
     * @param studentId the ID of the student
     * @param teacherId the ID of the teacher
     * @param testSessionId the ID of the test session
     * @return the ID of the created StudentTeacherRelation
     * @throws IllegalArgumentException if any of the IDs are null or if the test session does not exist
     */
    public Long addStudentTeacherRelation(Long studentId, Long teacherId, Long testSessionId) {
        if (studentId == null || teacherId == null || testSessionId == null) {
            throw new IllegalArgumentException("Student ID, Teacher ID, and Test Session ID must not be null");
        }

        // Check if the test session exists
        if (testSessionService.getTestSessionReplyById(testSessionId) == null) {
            throw new IllegalArgumentException("Test session with ID " + testSessionId + " does not exist");
        }

        // Check if the student and teacher exist
        if (userService.getUserById(studentId) == null || userService.getUserById(teacherId) == null) {
            throw new IllegalArgumentException("Student or Teacher with the provided ID does not exist");
        }

        if (userService.getUserById(teacherId).getRole() != Role.TEACHER) {
            throw new IllegalArgumentException("User with ID " + teacherId + " is not a teacher");
        }

        // Create and save the relation
        var relation = new com.talection.talection.model.datasharing.StudentTeacherRelation();
        relation.setStudentId(studentId);
        relation.setTeacherId(teacherId);
        relation.setTestSessionId(testSessionId);

        return studentTeacherRelationRepository.save(relation).getId();
    }

    /**
     * Retrieves all test sessions associated with a given teacher ID.
     *
     * @param teacherId the ID of the teacher
     * @return a collection of TestSessionReply objects associated with the teacher
     */
    public Collection<TestSessionReply> getTestSessionsByTeacherId(Long teacherId) {
        if (teacherId == null) {
            throw new IllegalArgumentException("Teacher ID must not be null");
        }

        return studentTeacherRelationRepository.findAllByTeacherId(teacherId).stream()
            .map(relation -> testSessionService.getTestSessionReplyById(relation.getTestSessionId()))
            .filter(Objects::nonNull)
            .toList();
    }

    /**
     * Retrieves all test sessions associated with a given student ID.
     *
     * @param studentId the ID of the student
     * @return a collection of TestSessionReply objects associated with the student
     * @throws IllegalArgumentException if the student ID is null
     */
    public Collection<TestSessionReply> getTestSessionsByStudentId(Long studentId) {
        if (studentId == null) {
            throw new IllegalArgumentException("Student ID must not be null");
        }

        return studentTeacherRelationRepository.findAllByStudentId(studentId).stream()
            .map(relation -> testSessionService.getTestSessionReplyById(relation.getTestSessionId()))
            .filter(Objects::nonNull)
            .toList();
    }

    /**
     * Deletes a Student-Teacher relation based on the provided student ID and teacher ID.
     *
     * @param studentId the ID of the student
     * @param teacherId the ID of the teacher
     * @throws IllegalArgumentException if either ID is null or if no relation is found
     */
    public void deleteStudentTeacherRelation(Long studentId, Long teacherId) {
        if (studentId == null || teacherId == null) {
            throw new IllegalArgumentException("Student ID and Teacher ID must not be null");
        }

        StudentTeacherRelation relation = studentTeacherRelationRepository.findByStudentIdAndTeacherId(studentId, teacherId);
        if (relation != null) {
            studentTeacherRelationRepository.delete(relation);
        } else {
            throw new IllegalArgumentException("No relation found for the given Student ID and Teacher ID");
        }
    }
}
