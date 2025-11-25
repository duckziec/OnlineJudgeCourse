package model;

public class DashboardStats {
    private String courseTitle;
    private int totalQuestions;
    private int solvedQuestions;
    private double progress;
    private double totalScore;

    public DashboardStats(String courseTitle, int totalQuestions, int solvedQuestions, double progress, double totalScore) {
        this.courseTitle = courseTitle;
        this.totalQuestions = totalQuestions;
        this.solvedQuestions = solvedQuestions;
        this.progress = progress;
        this.totalScore = totalScore;
    }

    public DashboardStats() {
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getSolvedQuestions() {
        return solvedQuestions;
    }

    public void setSolvedQuestions(int solvedQuestions) {
        this.solvedQuestions = solvedQuestions;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(double totalScore) {
        this.totalScore = totalScore;
    }
}
