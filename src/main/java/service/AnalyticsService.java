package service;

import dao.AnalyticsDAO;
import exception.DatabaseException;
import model.DashboardStats;
import model.EnrollmentInfo;
import model.Lesson;
import model.Submission;

import java.util.ArrayList;
import java.util.List;

public class AnalyticsService {
    private List<DashboardStats> listDashboard;

    private final AnalyticsDAO analyticsDAO;

    public AnalyticsService(AnalyticsDAO analyticsDAO) {
        this.analyticsDAO = analyticsDAO;
    }

    // thực hiện truy vấn UserDashboard
    public void reloadUserDashboard(int userId) {
        listDashboard = analyticsDAO.getUserCourseStats(userId);
    }

    // title - totalQuestions - solvedQuestions - progress - totalScore
    public void showUserDashboard(int userId) {
        int totalCourses = listDashboard.size();
        int totalSolved = listDashboard.stream().mapToInt(DashboardStats::getSolvedQuestions).sum();
        double totalScore = listDashboard.stream().mapToDouble(DashboardStats::getTotalScore).sum();

        System.out.println("\n📊 Tổng quan:");
        System.out.println("   • Khóa học đã đăng ký: " + totalCourses);
        System.out.println("   • Tổng bài tập đã làm: " + totalSolved);
        System.out.println("   • Tổng điểm: " + (int) totalScore);

        System.out.println("\n📈 Chi tiết theo khóa học:");
        for (DashboardStats s : listDashboard) {
            System.out.println("\n   " + s.getCourseTitle());
            System.out.println("   ├─ Tiến độ: " + s.getProgress() + "%");
            System.out.println("   ├─ Hoàn thành: " + s.getSolvedQuestions() + "/" + s.getTotalQuestions());
            System.out.println("   └─ Điểm: " + (int) s.getTotalScore());
        }
    }

    // title - totalQuestions - solvedQuestions - progress - totalScore
    public List<DashboardStats> getListDashboard() {
        return listDashboard;
    }

    // lấy danh sách khoá học kèm với progress
    public List<String> getUserEnrollments(int userId) {
        List<String> result = new ArrayList<>();
        List<EnrollmentInfo> list = AnalyticsDAO.getInstance().getUserCourseProgress(userId);
        for (EnrollmentInfo info : list) {
            String s = String.format("- %s (Tiến độ: %.0f%% - Điểm: %.0f)",
                    info.getCourseTitle(), info.getProgress(), info.getScore());
            result.add(s);
        }
        return result;
    }

    // reload danh sách lesson with status
    public List<Lesson> reloadLessonWithStatus(int userId, int courseId) {
        return analyticsDAO.getLessonsWithAcAndStatus(userId, courseId);
    }

    public void showCourseSubmissionHistory(int userId, int courseId) {
        List<Submission> submissionList = analyticsDAO.getCourseSubmissionHistory(userId, courseId);
        for (int i = 0; i < submissionList.size(); i++) {
            Submission submission = submissionList.get(i);
            System.out.printf("%d. %s | Trạng thái: %s | Điểm: %.0f | Test passed: %d/%d\n", (i + 1), submission.getSubmitTime(), submission.getStatus(), submission.getScore(), submission.getPassedTest(), submission.getTotalTest());
        }
    }

    public List<Submission> getQuestionSubmissionHistory(int userId, int courseId, int questionId) {
        try {
            return analyticsDAO.getQuestionSubmissionHistory(userId, courseId, questionId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("Get question submission history failed:");
        }
    }

    public double getProgressCourse(int enrollmentId) {
        try {
            return analyticsDAO.calculateProgress(enrollmentId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DatabaseException("Calculate progress failed:");
        }
    }

}

