package com.talection.talection.repository.tests;

import com.talection.talection.enums.TestType;
import com.talection.talection.model.tests.TestTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestTemplateRepository extends JpaRepository<TestTemplate, Long>{
    public Optional<TestTemplate> findByTestType(TestType testType);
}
