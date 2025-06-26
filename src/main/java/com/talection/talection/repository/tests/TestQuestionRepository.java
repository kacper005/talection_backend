package com.talection.talection.repository.tests;

import com.talection.talection.model.tests.TestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestQuestionRepository extends JpaRepository<TestQuestion, Long> {
}
