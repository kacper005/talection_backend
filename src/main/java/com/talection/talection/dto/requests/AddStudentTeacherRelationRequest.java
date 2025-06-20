package com.talection.talection.dto.requests;

public class AddStudentTeacherRelationRequest {
    private Long teacherId;
    private Long testSessionId;

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTestSessionId(Long testSessionId) {
        this.testSessionId = testSessionId;
    }

    public Long getTestSessionId() {
        return testSessionId;
    }
}
