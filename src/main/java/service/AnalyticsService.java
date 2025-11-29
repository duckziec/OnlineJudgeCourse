package service;

import dao.AnalyticsDAO;
import exception.DatabaseException;
import model.Lesson;
import model.Submission;

import java.util.List;

public class AnalyticsService {

    private final AnalyticsDAO analyticsDAO;

    public AnalyticsService(AnalyticsDAO analyticsDAO) {
        this.analyticsDAO = analyticsDAO;
    }

    // reload danh sách lesson with status
    public List<Lesson> reloadLessonWithStatus(int userId, int courseId) {
        return analyticsDAO.getLessonsWithAcAndStatus(userId, courseId);
    }

    public List<Submission> getQuestionSubmissionHistory(int userId, int courseId, int questionId) {
        try {
            return analyticsDAO.getQuestionSubmissionHistory(userId, courseId, questionId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("Get question submission history failed:");
        }
    }

    public double getProgressCourse(int userId, int courseId) {
        try {
            return analyticsDAO.calculateProgress(userId, courseId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("Calculate progress failed:");
        }
    }

}

