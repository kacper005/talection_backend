package com.talection.talection.repository.datasharing;

import com.talection.talection.model.datasharing.StudentTeacherRelation;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
