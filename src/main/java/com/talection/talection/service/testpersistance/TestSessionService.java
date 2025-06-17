package com.talection.talection.service.testpersistance;

import com.talection.talection.exception.InvalidChoiceException;
import com.talection.talection.exception.TestTemplateNotFoundException;
import com.talection.talection.exception.UserNotFoundException;
import com.talection.talection.model.testpersistance.TestSession;
import com.talection.talection.repository.testpersistance.TestChoiceRepository;
import com.talection.talection.repository.testpersistance.TestSessionRepository;
import com.talection.talection.repository.tests.TestTemplateRepository;
import com.talection.talection.repository.userrelated.UserRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class TestSessionService {
    private final TestSessionRepository testSessionRepository;
    private final TestChoiceRepository testChoiceRepository;
    private final UserRepository userRepository;
    private final TestTemplateRepository testTemplateRepository;

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(TestSessionService.class);

    public TestSessionService(TestSessionRepository testSessionRepository, TestChoiceRepository testChoiceRepository, UserRepository userRepository,
                              TestTemplateRepository testTemplateRepository) {
        this.testSessionRepository = testSessionRepository;
        this.testChoiceRepository = testChoiceRepository;
        this.userRepository = userRepository;
        this.testTemplateRepository = testTemplateRepository;
    }

    public Long addTestSession(TestSession testSession) {
        if (testSession.getTestTemplateId() == null || testSession.getUserId() == null) {
            throw new IllegalArgumentException("TestTemplateId and UserId must not be null");
        }
        if (testSession.getStartTime() == null || testSession.getEndTime() == null) {
            throw new IllegalArgumentException("StartTime and EndTime must not be null");
        }
        if (testSession.getChoices().isEmpty() || testSession.getChoices() == null) {
            throw new IllegalArgumentException("Choices must not be empty or null");
        }

        userRepository.findById(testSession.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found with id: " + testSession.getUserId()));
        testTemplateRepository.findById(testSession.getTestTemplateId()).orElseThrow(() -> new TestTemplateNotFoundException("TestTemplate not found with id: " + testSession.getTestTemplateId()));

        testSession.getChoices().forEach(choice -> {
            if (choice.getQuestionId() == null || choice.getSelectedOptionId() == null) {
                throw new InvalidChoiceException("Choice must have a valid QuestionId and SelectedOptionId");
            }
            testChoiceRepository.save(choice);
        });
        TestSession savedSession = testSessionRepository.save(testSession);
        return savedSession.getId();
    }

    public Collection<TestSession> findAllTestSessionsForUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId must not be null");
        }
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        return testSessionRepository.findAllByUserId(userId);
    }
}
