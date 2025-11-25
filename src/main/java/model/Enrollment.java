package model;

import java.time.LocalDateTime;

public class Enrollment {
    private int enrollmentId;
    private int userId;
    private int courseId;
    private LocalDateTime enrolledAt;


    public Enrollment() {
    }

    public Enrollment(int enrollmentId, int userId, int courseId, LocalDateTime enrolledAt) {
        this.enrollmentId = enrollmentId;
        this.userId = userId;
        this.courseId = courseId;
        this.enrolledAt = enrolledAt;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }


    @Override
    public String toString() {
        return "Enrollment{id=" + enrollmentId + ", userId=" + userId + ", courseId=" + courseId + "}";
    }
}