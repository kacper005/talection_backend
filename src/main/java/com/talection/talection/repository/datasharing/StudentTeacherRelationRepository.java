package com.talection.talection.repository.datasharing;

import com.talection.talection.model.datasharing.StudentTeacherRelation;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface StudentTeacherRelationRepository extends JpaRepository<StudentTeacherRelation, Long> {
    /**
     * Finds a StudentTeacherRelation by student ID and teacher ID.
     *
     * @param studentId the ID of the student
     * @param teacherId the ID of the teacher
     * @return the StudentTeacherRelation if found, otherwise null
     */
    StudentTeacherRelation findByStudentIdAndTeacherId(Long studentId, Long teacherId);

    /**
     * Finds all StudentTeacherRelations by student ID or teacher ID.
     *
     * @param id the ID of the student or teacher
     * @return a collection of StudentTeacherRelations associated with the given ID
     */
    @Query("SELECT r FROM StudentTeacherRelation r WHERE r.studentId = :id OR r.teacherId = :id")
    Collection<StudentTeacherRelation> findAllByStudentIdOrTeacherId(@Param("id") Long id);

    /**
     * Finds all StudentTeacherRelations by teacher ID.
     *
     * @param teacherId the ID of the teacher
     * @return a collection of StudentTeacherRelations associated with the teacher
     */
    Collection<StudentTeacherRelation> findAllByTeacherId(Long teacherId);

    /**
     * Finds all StudentTeacherRelations by student ID.
     *
     * @param studentId the ID of the student
     * @return a collection of StudentTeacherRelations associated with the student
     */
    Collection<StudentTeacherRelation> findAllByStudentId(Long studentId);

    /**
     * Finds all StudentTeacherRelations by student ID and test session ID.
     *
     * @param studentId the ID of the student
     * @param testSessionId the ID of the test session
     * @return a collection of StudentTeacherRelations associated with the student and test session
     */
    Collection<StudentTeacherRelation> findAllByStudentIdAndTestSessionId(Long studentId, Long testSessionId);
}
