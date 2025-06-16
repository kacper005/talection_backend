package com.talection.talection.service.tests;

import com.talection.talection.model.tests.TestTemplate;
import com.talection.talection.repository.tests.TestTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TestTemplateService {
    private final TestTemplateRepository testTemplateRepository;

    public TestTemplateService(TestTemplateRepository testTemplateRepository) {
        this.testTemplateRepository = testTemplateRepository;
    }

    public Collection<TestTemplate> getAllTestTemplates() {
        return testTemplateRepository.findAll();
    }
}
