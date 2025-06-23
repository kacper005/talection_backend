package com.talection.talection.dto.replies;

public class TeacherRelationReply {
    private Long relationId;
    private String teacherEmail;
    private String teacherName;

    /**
     * Gets the relation ID.
     *
     * @return the relation ID
     */
    public Long getRelationId() {
        return relationId;
    }

    /**
     * Sets the relation ID.
     *
     * @param relationId the relation ID to set
     */
    public void setRelationId(Long relationId) {
        this.relationId = relationId;
    }

    /**
     * Gets the teacher's email.
     *
     * @return the teacher's email
     */
    public String getTeacherEmail() {
        return teacherEmail;
    }

    /**
     * Sets the teacher's email.
     *
     * @param teacherEmail the teacher's email to set
     */
    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }

    /**
     * Gets the teacher's name.
     *
     * @return the teacher's name
     */
    public String getTeacherName() {
        return teacherName;
    }

    /**
     * Sets the teacher's name.
     *
     * @param teacherName the teacher's name to set
     */
    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
}
