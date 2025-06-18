package com.talection.talection.service.testpersistance;

import com.talection.talection.dto.replies.TestChoiceReply;
import com.talection.talection.dto.replies.TestSessionReply;
import com.talection.talection.enums.TestOptionType;
import com.talection.talection.exception.*;
import com.talection.talection.model.testpersistance.TestChoice;
import com.talection.talection.model.testpersistance.TestSession;
import com.talection.talection.model.tests.TestOption;
import com.talection.talection.model.tests.TestQuestion;
import com.talection.talection.model.tests.TestTemplate;
import com.talection.talection.model.userrelated.StudyProgram;
import com.talection.talection.model.userrelated.User;
import com.talection.talection.repository.testpersistance.TestChoiceRepository;
import com.talection.talection.repository.testpersistance.TestSessionRepository;
import com.talection.talection.repository.tests.TestTemplateRepository;
import com.talection.talection.repository.userrelated.UserRepository;
import com.talection.talection.service.tests.TestOptionService;
import com.talection.talection.service.tests.TestQuestionService;
import com.talection.talection.service.userrelated.StudyProgramService;
import com.talection.talection.service.userrelated.UserService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Service for managing test sessions.
 * This service handles the creation, retrieval, and formatting of test sessions for users.
 */
@Service
public class TestSessionService {
    private final TestSessionRepository testSessionRepository;
    private final TestChoiceRepository testChoiceRepository;
    private final TestTemplateRepository testTemplateRepository;

    private final TestQuestionService testQuestionService;
    private final TestOptionService testOptionService;
    private final UserService userService;


    public TestSessionService(TestSessionRepository testSessionRepository, TestChoiceRepository testChoiceRepository, UserService userService,
                              TestTemplateRepository testTemplateRepository, TestQuestionService testQuestionService, TestOptionService testOptionService) {
        this.testSessionRepository = testSessionRepository;
        this.testChoiceRepository = testChoiceRepository;
        this.userService = userService;
        this.testTemplateRepository = testTemplateRepository;
        this.testQuestionService = testQuestionService;
        this.testOptionService = testOptionService;
    }

    /**
     * Adds a new test session for a user.
     *
     * @param testSession the test session to add
     * @return the ID of the saved test session
     * @throws IllegalArgumentException if any required fields are null or empty
     * @throws UserNotFoundException if the user does not exist
     * @throws TestTemplateNotFoundException if the test template does not exist
     * @throws InvalidChoiceException if any choice in the session is invalid
     */
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

        userService.getUserById(testSession.getUserId());
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

    /**
     * Retreives all test sessions for a specific user.
     *
     * @param userId the ID of the user whose test sessions are to be retrieved
     * @return a collection of test sessions for the specified user
     */
    public Collection<TestSession> findAllTestSessionsForUser(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId must not be null");
        }
        if (userService.getUserById(userId) == null) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        return testSessionRepository.findAllByUserId(userId);
    }

    /**
     * Retrieves all test sessions for a specific user and formats them into replies.
     *
     * @param userId the ID of the user whose test sessions are to be formatted
     * @return a collection of formatted test session replies for the specified user
     * @throws IllegalArgumentException if the userId is null
     * @throws UserNotFoundException if the user does not exist
     */
    public Collection<TestSessionReply> getAllTestSessionsForUserFormatted(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("UserId must not be null");
        }
        if (userService.getUserById(userId) == null) {
            throw new UserNotFoundException("User not found with id: " + userId);
        }
        return testSessionRepository.findAllByUserId(userId).stream()
                .map(this::convertToReply)
                .toList();
    }

    /**
     * Retrieves a specific test session by its ID and formats it into a reply.
     *
     * @param id the ID of the test session to retrieve
     * @return the formatted test session reply
     * @throws IllegalArgumentException if the id is null
     * @throws  if the test session does not exist
     */
    private TestSessionReply getTestSessionReplyById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        TestSession testSession = testSessionRepository.findById(id)
                .orElseThrow(() -> new TestSessionNotFoundException("TestSession not found with id: " + id));
        return convertToReply(testSession);
    }

    /**
     * Converts a TestSession object into a TestSessionReply object.
     *
     * @param testSession the TestSession object to convert
     * @return the converted TestSessionReply object
     */
    private TestSessionReply convertToReply(TestSession testSession) {
        if (testSession == null) {
            return null;
        }
        TestSessionReply reply = new TestSessionReply();
        reply.setStartTime(testSession.getStartTime());
        reply.setEndTime(testSession.getEndTime());
        reply.setChoices(
                testSession.getChoices().stream()
                        .map(this::convertToChoiceReply)
                        .toList()
        );

        User user = userService.getUserById(testSession.getUserId());
        reply.setUserEmail(user.getEmail());
        reply.setUserRole(user.getRole());

        TestTemplate testTemplate = testTemplateRepository.findById(testSession.getTestTemplateId())
                .orElseThrow(() -> new TestTemplateNotFoundException("TestTemplate not found with id: " + testSession.getTestTemplateId()));
        reply.setTestName(testTemplate.getName());
        reply.setTestDescription(testTemplate.getDescription());

        return reply;
    }

    /**
     * Converts a TestChoice object into a TestChoiceReply object.
     *
     * @param choice the TestChoice object to convert
     * @return the converted TestChoiceReply object
     * @throws TestQuestionNotFoundException if the question associated with the choice does not exist
     * @throws TestOptionNotFoundException if the selected option associated with the choice does not exist
     */
    private TestChoiceReply convertToChoiceReply(TestChoice choice) {
        if (choice == null) {
            return null;
        }
        TestQuestion question = testQuestionService.findById(choice.getQuestionId());
        if (question == null) {
            throw new TestQuestionNotFoundException("TestQuestion not found with id: " + choice.getQuestionId());
        }
        TestOption selectedOption = testOptionService.findById(choice.getSelectedOptionId());
        if (selectedOption == null) {
            throw new TestOptionNotFoundException("Selected option not found with id: " + choice.getSelectedOptionId());
        }

        TestChoiceReply reply = new TestChoiceReply();
        reply.setQuestion(question.getQuestionText());

        if (selectedOption.getType() == TestOptionType.LIKERT_SCALE) {
            reply.setAnswer(getLikertScaleText(selectedOption.getAgreementLevel()));
        } else if (selectedOption.getType() == TestOptionType.MULTIPLE_CHOICE) {
            reply.setAnswer(selectedOption.getOptionText());
        } else {
            throw new UnsupportedOperationException("Unsupported TestOptionType: " + selectedOption.getType());
        }

        return reply;
    }

    /**
     * Converts a Likert scale value to its corresponding text representation.
     *
     * @param scaleValue the Likert scale value (0-5)
     * @return the text representation of the Likert scale value
     * @throws IllegalArgumentException if the scaleValue is not in the range 0-5
     */
    private String getLikertScaleText(int scaleValue) {
        return switch (scaleValue) {
            case 0 -> "Not answered";
            case 1 -> "Strongly Disagree";
            case 2 -> "Disagree";
            case 3 -> "Neutral";
            case 4 -> "Agree";
            case 5 -> "Strongly Agree";
            default -> throw new IllegalArgumentException("Invalid Likert scale value: " + scaleValue);
        };
    }
}
