package com.talection.talection.dto.replies;

public class TeacherReply {
    private Long teacherId;
    private String teacherEmail;
    private String teacherName;

    /**
     * Gets the teacher's ID.
     *
     * @return the teacher's ID
     */
    public Long getTeacherId() {
        return teacherId;
    }

    /**
     * Sets the teacher's ID.
     *
     * @param teacherId the teacher's ID to set
     */
    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
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
