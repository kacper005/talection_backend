package com.talection.talection.controller.tests;

import com.talection.talection.enums.TestType;
import com.talection.talection.exception.TestTemplateNotFoundException;
import com.talection.talection.model.tests.TestTemplate;
import com.talection.talection.service.tests.TestTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Controller for managing test templates.
 */
@RestController
@RequestMapping("/test")
public class TestTemplateController {
    TestTemplateService testTemplateService;
    Logger logger = LoggerFactory.getLogger(TestTemplateController.class);

    public TestTemplateController(TestTemplateService testTemplateService) {
        this.testTemplateService = testTemplateService;
    }

    /**
     * Endpoint to retrieve all test templates.
     *
     * @return ResponseEntity containing a collection of test templates
     */
    @GetMapping("/get-all")
    public ResponseEntity<Collection<TestTemplate>> getAllTestTemplates() {
        logger.info("Fetching all test templates");
        return new ResponseEntity<>(testTemplateService.getAllTestTemplates(),
                                    org.springframework.http.HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve a test template by its ID.
     *
     * @param id the ID of the test template
     * @return ResponseEntity containing the test template or an error status
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<TestTemplate> getTestTemplateById(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        logger.info("Attempting to fetch test template with ID: {}", id);

        try {
            TestTemplate testTemplate = testTemplateService.getTestTemplateById(id);
            logger.info("Successfully fetched test template with ID: {}", id);
            return ResponseEntity.ok(testTemplate);
        } catch (TestTemplateNotFoundException e) {
            logger.error("Error fetching test template with ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(404).build();
        }
    }

    /**
     * Endpoint to retrieve a test template by its test type.
     *
     * @param testType the type of the test
     * @return ResponseEntity containing the test template or an error status
     */
    @GetMapping("/get-by-test-type/{testType}")
    public ResponseEntity<TestTemplate> getTestTemplateByTestType(@PathVariable TestType testType) {
        if (testType == null) {
            return ResponseEntity.badRequest().build();
        }
        logger.info("Attempting to fetch test template with test type: {}", testType);

        try {
            TestTemplate testTemplate = testTemplateService.getTestTemplateByTestType(testType);
            logger.info("Successfully fetched test template with test type: {}", testType);
            return ResponseEntity.ok(testTemplate);
        } catch (TestTemplateNotFoundException e) {
            logger.error("Error fetching test template with test type {}: {}", testType, e.getMessage());
            return ResponseEntity.status(404).build();
        }
    }

    //TODO: Put for updating description
}
