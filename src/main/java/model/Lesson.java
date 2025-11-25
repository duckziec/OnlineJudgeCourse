package model;

public class Lesson {
    private int lessonId;
    private int courseId;
    private String title;
    private String description;
    private int orderIndex;
    private String category;
    private String difficulty;

    private int acpt = 0;
    private double acptPercent = 0.0;

    private String status = "";

    public Lesson() {
    }

    public Lesson(int lessonId, int courseId, String title, String description, int orderIndex, String category, String difficulty) {
        this.lessonId = lessonId;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.orderIndex = orderIndex;
        this.category = category;
        this.difficulty = difficulty;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getStatus() {
        return status;
    }

    public int getAcpt() {
        return acpt;
    }

    public void setAcpt(int acpt) {
        this.acpt = acpt;
    }

    public double getAcptPercent() {
        return acptPercent;
    }

    public void setAcptPercent(double acptPercent) {
        this.acptPercent = acptPercent;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Lesson{id=" + lessonId + ", title='" + title + "'}";
    }
}