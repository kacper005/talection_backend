package com.talection.talection.dto.replies;

/**
 * Data Transfer Object for Test Choice Reply.
 * This class represents a reply to a test question with a choice.
 */
public class TestChoiceReply {
    private String question;
    private String answer;

    /**
     * Sets the question for this test choice reply.
     *
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets the question for this test choice reply.
     *
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the answer for this test choice reply.
     *
     * @param answer the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * Gets the answer for this test choice reply.
     *
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }
}
