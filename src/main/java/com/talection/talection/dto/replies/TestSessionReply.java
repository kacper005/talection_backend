package com.talection.talection.dto.replies;

import com.talection.talection.enums.Role;
import com.talection.talection.model.testpersistance.TestChoice;

import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object for test session replies.
 * This class encapsulates the necessary information
 * for a user's test session, including user details,
 * test metadata, and the choices made during the test.
 */
public class TestSessionReply {
    private String userEmail;
    private Role userRole;
    private String testName;
    private String testDescription;
    private Date startTime;
    private Date endTime;
    private List<TestChoiceReply> choices;

    /**
     * Gets the email of the user who took the test.
     *
     * @return the user's email address
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Sets the email of the user who took the test.
     *
     * @param userEmail the user's email address to set
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * Gets the role of the user who took the test.
     *
     * @return the user's role
     */
    public Role getUserRole() {
        return userRole;
    }

    /**
     * Sets the role of the user who took the test.
     *
     * @param userRole the user's role to set
     */
    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    /**
     * Gets the name of the test.
     *
     * @return the name of the test
     */
    public String getTestName() {
        return testName;
    }

    /**
     * Sets the name of the test.
     *
     * @param testName the name of the test to set
     */
    public void setTestName(String testName) {
        this.testName = testName;
    }

    /**
     * Gets the description of the test.
     *
     * @return the description of the test
     */
    public String getTestDescription() {
        return testDescription;
    }

    /**
     * Sets the description of the test.
     *
     * @param testDescription the description of the test to set
     */
    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    /**
     * Gets the start time of the test session.
     *
     * @return the start time of the test session
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time of the test session.
     *
     * @param startTime the start time to set
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the test session.
     *
     * @return the end time of the test session
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time of the test session.
     *
     * @param endTime the end time to set
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the list of choices made during the test.
     *
     * @return the list of test choice replies
     */
    public List<TestChoiceReply> getChoices() {
        return choices;
    }

    /**
     * Sets the list of choices made during the test.
     *
     * @param choices the list of test choice replies to set
     */
    public void setChoices(List<TestChoiceReply> choices) {
        this.choices = choices;
    }
}
