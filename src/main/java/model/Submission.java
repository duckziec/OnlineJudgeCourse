package model;

import java.time.LocalDateTime;

public class Submission {
    private int submissionId;
    private int enrollmentId;
    private int questionId;
    private String code;
    private String language; // enum: python, cpp, java, js
    private LocalDateTime submitTime;
    private String status;   // enum: Pending, Running, Accepted, Wrong Answer, ...
    private double score;
    private String message;
    private int passedTest;
    private int totalTest;

    public Submission() {
    }

    public Submission(int submissionId, int enrollmentId, int questionId, String code,
                      String language, LocalDateTime submitTime, String status,
                      double score, String message) {
        this.submissionId = submissionId;
        this.enrollmentId = enrollmentId;
        this.questionId = questionId;
        this.code = code;
        this.language = language;
        this.submitTime = submitTime;
        this.status = status;
        this.score = score;
        this.message = message;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDateTime getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(LocalDateTime submitTime) {
        this.submitTime = submitTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getPassedTest() {
        return passedTest;
    }

    public void setPassedTest(int passedTest) {
        this.passedTest = passedTest;
    }

    public int getTotalTest() {
        return totalTest;
    }

    public void setTotalTest(int totalTest) {
        this.totalTest = totalTest;
    }

    @Override
    public String toString() {
        return "Submission{id=" + submissionId + ", lang=" + language + ", status='" + status + "'}";
    }
}