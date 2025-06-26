package com.talection.talection.service.tests;

import com.talection.talection.enums.TestType;
import com.talection.talection.exception.TestTemplateNotFoundException;
import com.talection.talection.model.tests.TestTemplate;
import com.talection.talection.repository.tests.TestTemplateRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service for managing test templates.
 */
@Service
public class TestTemplateService {
    private final TestTemplateRepository testTemplateRepository;

    public TestTemplateService(TestTemplateRepository testTemplateRepository) {
        this.testTemplateRepository = testTemplateRepository;
    }

    /**
     * Retrieves all test templates.
     *
     * @return a collection of all test templates
     */
    public Collection<TestTemplate> getAllTestTemplates() {
        return testTemplateRepository.findAll();
    }

    /**
     * Retrieves a test template by its ID.
     *
     * @param id the ID of the test template
     * @return the test template with the specified ID
     * @throws TestTemplateNotFoundException if no test template is found with the given ID
     */
    public TestTemplate getTestTemplateById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return testTemplateRepository.findById(id)
                .orElseThrow(() -> new TestTemplateNotFoundException("Test template not found with ID: " + id));
    }

    /**
     * Retrieves a test template by its test type.
     *
     * @param testType the type of the test
     * @return the test template with the specified test type
     * @throws TestTemplateNotFoundException if no test template is found with the given test type
     */
    public TestTemplate getTestTemplateByTestType(TestType testType) {
        if (testType == null) {
            throw new IllegalArgumentException("Test type cannot be null");
        }
        return testTemplateRepository.findByTestType(testType)
                .orElseThrow(() -> new TestTemplateNotFoundException("Test template not found with test type: " + testType));
    }

    /**
     * Updates the description of a test template.
     *
     * @param templateId the ID of the test template to update
     * @param description the new description for the test template
     * @throws IllegalArgumentException if the templateId or description is null or empty
     * @throws TestTemplateNotFoundException if no test template is found with the given ID
     */
    public void updateTestTemplateDescription(Long templateId, String description) {
        if (templateId == null || description == null || description.isEmpty()) {
            throw new IllegalArgumentException("Template ID and description must not be null or empty");
        }

        TestTemplate testTemplate = testTemplateRepository.findById(templateId)
                .orElseThrow(() -> new TestTemplateNotFoundException("Test template not found with ID: " + templateId));

        testTemplate.setDescription(description);
        testTemplateRepository.save(testTemplate);
    }
}
