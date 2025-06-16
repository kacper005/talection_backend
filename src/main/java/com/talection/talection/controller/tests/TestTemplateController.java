package com.talection.talection.controller.tests;

import com.talection.talection.model.tests.TestTemplate;
import com.talection.talection.service.tests.TestTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;


@RestController
@RequestMapping("/test")
public class TestTemplateController {
    TestTemplateService testTemplateService;
    Logger logger = LoggerFactory.getLogger(TestTemplateController.class);

    public TestTemplateController(TestTemplateService testTemplateService) {
        this.testTemplateService = testTemplateService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<Collection<TestTemplate>> getAllTestTemplates() {
        logger.info("Fetching all test templates");
        return new ResponseEntity<>(testTemplateService.getAllTestTemplates(),
                                    org.springframework.http.HttpStatus.OK);
    }
}
