package model;

import java.util.ArrayList;
import java.util.List;

/**
 * SubmissionResultData
 * ----------------
 * Chứa kết quả chấm bài tổng thể
 */
public class SubmissionResultData {
    private String overallStatus;
    private int passedCount;
    private int totalCount;
    private double score;
    private double maxScore;
    private double totalTime;
    private double maxTime;
    private double avgMemory;
    private List<TestCaseResultData> testCases;

    public SubmissionResultData() {
        this.testCases = new ArrayList<>();
    }

    // Getters and Setters
    public String getOverallStatus() {
        return overallStatus;
    }

    public void setOverallStatus(String overallStatus) {
        this.overallStatus = overallStatus;
    }

    public int getPassedCount() {
        return passedCount;
    }

    public void setPassedCount(int passedCount) {
        this.passedCount = passedCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(double maxScore) {
        this.maxScore = maxScore;
    }

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public double getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(double maxTime) {
        this.maxTime = maxTime;
    }

    public double getAvgMemory() {
        return avgMemory;
    }

    public void setAvgMemory(double avgMemory) {
        this.avgMemory = avgMemory;
    }

    public List<TestCaseResultData> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCaseResultData> testCases) {
        this.testCases = testCases;
    }

    public void addTestCase(TestCaseResultData testCase) {
        this.testCases.add(testCase);
    }
}