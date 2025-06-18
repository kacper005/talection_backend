package com.talection.talection.service.tests;

import com.talection.talection.exception.TestOptionNotFoundException;
import com.talection.talection.model.tests.TestOption;
import com.talection.talection.repository.tests.TestOptionRepository;
import org.springframework.stereotype.Service;

@Service
public class TestOptionService {
    private final TestOptionRepository testOptionRepository;

    public TestOptionService(TestOptionRepository testOptionRepository) {
        this.testOptionRepository = testOptionRepository;
    }

    public TestOption findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return testOptionRepository.findById(id)
                .orElseThrow(() -> new TestOptionNotFoundException("Test option not found with ID: " + id));
    }
}
