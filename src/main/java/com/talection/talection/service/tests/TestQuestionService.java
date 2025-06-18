package com.talection.talection.service.tests;

import com.talection.talection.exception.TestQuestionNotFoundException;
import com.talection.talection.model.tests.TestQuestion;
import com.talection.talection.repository.tests.TestQuestionRepository;
import org.springframework.stereotype.Service;

@Service
public class TestQuestionService {
    private final TestQuestionRepository testQuestionRepository;

    public TestQuestionService(TestQuestionRepository testQuestionRepository) {
        this.testQuestionRepository = testQuestionRepository;
    }

    public TestQuestion findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return testQuestionRepository.findById(id)
                .orElseThrow(() -> new TestQuestionNotFoundException("Test question not found with ID: " + id));
    }
}
