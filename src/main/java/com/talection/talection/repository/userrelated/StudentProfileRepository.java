package com.talection.talection.repository.userrelated;

import com.talection.talection.model.userrelated.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {
}
