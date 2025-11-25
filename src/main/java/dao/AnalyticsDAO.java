package dao;

import model.DashboardStats;
import model.EnrollmentInfo;
import model.Lesson;
import model.Submission;

import java.util.List;

public class AnalyticsDAO extends BaseDAO {

    private static AnalyticsDAO analyticsDAO;

    private AnalyticsDAO() {
    }

    public static AnalyticsDAO getInstance() {
        if (analyticsDAO == null) {
            analyticsDAO = new AnalyticsDAO();
        }
        return analyticsDAO;
    }

    public List<EnrollmentInfo> getUserCourseProgress(int userId) {
        String sql = """
                    SELECT e.enrollment_id, e.user_id, e.course_id,
                           c.title AS course_title,
                           c.description AS course_description,
                           ROUND(
                                (
                                    SELECT COUNT(DISTINCT s.question_id)
                                    FROM submissions s
                                    WHERE s.enrollment_id = e.enrollment_id
                                      AND s.status = 'Accepted'
                                ) / NULLIF(
                                    (SELECT COUNT(DISTINCT q.question_id)
                                     FROM coding_questions q
                                     JOIN lessons l ON q.lesson_id = l.lesson_id
                                     WHERE l.course_id = c.course_id), 0
                                ) * 100, 2
                           ) AS progress_percent,
                           ROUND(
                                (
                                    SELECT AVG(s.score)
                                    FROM submissions s
                                    WHERE s.enrollment_id = e.enrollment_id
                                      AND s.status = 'Accepted'
                                ), 2
                           ) AS avg_score
                    FROM enrollments e
                    JOIN courses c ON e.course_id = c.course_id
                    WHERE e.user_id = ?
                """;

        return executeQuery(sql, rs -> {
            EnrollmentInfo info = new EnrollmentInfo();
            info.setEnrollmentId(rs.getInt("enrollment_id"));
            info.setUserId(rs.getInt("user_id"));
            info.setCourseId(rs.getInt("course_id"));
            info.setCourseTitle(rs.getString("course_title"));
            info.setCourseDescription(rs.getString("course_description"));
            info.setProgress(rs.getDouble("progress_percent"));
            info.setScore(rs.getDouble("avg_score"));
            return info;
        }, userId);
    }

    public List<DashboardStats> getUserCourseStats(int userId) {
        String sql = """
                    SELECT 
                        c.title AS courseTitle,
                        COUNT(DISTINCT cq.question_id) AS totalQuestions,
                        COALESCE(SUM(best.solved_flag), 0) AS solvedQuestions,
                        ROUND(
                            CASE WHEN COUNT(DISTINCT cq.question_id) = 0 THEN 0
                                 ELSE COALESCE(SUM(best.solved_flag), 0) / COUNT(DISTINCT cq.question_id) * 100
                            END, 2
                        ) AS progress,
                        COALESCE(SUM(best.best_score), 0) AS totalScore
                    FROM enrollments e
                    JOIN courses c ON e.course_id = c.course_id
                    LEFT JOIN lessons l ON c.course_id = l.course_id
                    LEFT JOIN coding_questions cq ON l.lesson_id = cq.lesson_id
                    LEFT JOIN (
                        SELECT 
                            question_id,
                            enrollment_id,
                            MAX(score) AS best_score,
                            MAX(CASE WHEN status = 'Accepted' THEN 1 ELSE 0 END) AS solved_flag
                        FROM submissions
                        GROUP BY question_id, enrollment_id
                    ) AS best 
                        ON best.enrollment_id = e.enrollment_id
                       AND best.question_id = cq.question_id
                    WHERE e.user_id = ?
                    GROUP BY c.course_id, c.title;
                """;

        return executeQuery(sql, rs -> {
            DashboardStats stats = new DashboardStats();
            stats.setCourseTitle(rs.getString("courseTitle"));
            stats.setTotalQuestions(rs.getInt("totalQuestions"));
            stats.setSolvedQuestions(rs.getInt("solvedQuestions"));
            stats.setProgress(rs.getDouble("progress"));
            stats.setTotalScore(rs.getDouble("totalScore"));
            return stats;
        }, userId);
    }

    public List<EnrollmentInfo> getEnrollmentsWithCoursesByUser(int userId) {
        String sql = """
                    SELECT e.enrollment_id, e.user_id, e.progress,
                           c.course_id, c.title, c.description,
                           e.progress * 10 AS score
                    FROM enrollments e
                    JOIN courses c ON e.course_id = c.course_id
                    WHERE e.user_id = ?
                """;

        return executeQuery(sql, rs -> {
            EnrollmentInfo info = new EnrollmentInfo();
            info.setEnrollmentId(rs.getInt("enrollment_id"));
            info.setUserId(rs.getInt("user_id"));
            info.setCourseId(rs.getInt("course_id"));
            info.setCourseTitle(rs.getString("title"));
            info.setCourseDescription(rs.getString("description"));
            info.setProgress(rs.getDouble("progress"));
            info.setScore(rs.getDouble("score"));
            return info;
        }, userId);
    }

    /**
     * | Thành phần                                | Ý nghĩa                                                                   |
     * | ----------------------------------------- | ------------------------------------------------------------------------- |
     * | `SubmissionStats`                         | Gom tổng số lần nộp và số lần `Accepted` cho từng `question_id`.          |
     * | `LessonStats`                             | Tính tổng `total_submissions` và `accepted_submissions` theo `lesson_id`. |
     * | `ls` (LessonStats)                        | Dùng để tính `%AC` và tổng số `AC`.                                       |
     * | `CASE` trong SELECT chính                 | Xác định `status` của bài học cho **user cụ thể**, theo logic bạn đã có.  |
     * | `WHERE e.user_id = ? AND e.course_id = ?` | Giới hạn trong khóa học của người dùng.                                   |
     * | `GROUP BY l.lesson_id`                    | Gộp dữ liệu đúng cấp độ bài học.                                          |
     */
    public List<Lesson> getLessonsWithAcAndStatus(int userId, int courseId) {
        String sql = """
                SELECT
                    l.lesson_id,
                    l.course_id,
                    l.title,
                    l.description,
                    l.order_index,
                    l.category,
                    l.difficult,
                    COALESCE(stats.accepted_submissions, 0) AS ac,
                    CASE
                        WHEN COALESCE(stats.total_submissions, 0) > 0
                        THEN (COALESCE(stats.accepted_submissions, 0) * 100.0 / stats.total_submissions)
                        ELSE 0
                    END AS ac_percent,
                    CASE
                        WHEN COUNT(DISTINCT cq.question_id) = 0 THEN 'Chưa có bài tập'
                        WHEN COUNT(DISTINCT cq.question_id) = COUNT(DISTINCT CASE WHEN s.status = 'Accepted' THEN cq.question_id END)
                            THEN 'Done'
                        ELSE 'NotDone'
                    END AS status
                FROM lessons l
                JOIN courses c ON l.course_id = c.course_id
                JOIN enrollments e ON e.course_id = c.course_id
                LEFT JOIN coding_questions cq ON l.lesson_id = cq.lesson_id
                LEFT JOIN submissions s ON s.question_id = cq.question_id AND s.enrollment_id = e.enrollment_id
                LEFT JOIN (
                    SELECT
                        cq.lesson_id AS lesson_id,
                        SUM(sub_counts.total_submissions) AS total_submissions,
                        SUM(sub_counts.accepted_submissions) AS accepted_submissions
                    FROM coding_questions cq
                    JOIN (
                        SELECT
                            question_id,
                            COUNT(submission_id) AS total_submissions,
                            SUM(CASE WHEN status = 'Accepted' THEN 1 ELSE 0 END) AS accepted_submissions
                        FROM submissions
                        GROUP BY question_id
                    ) sub_counts ON cq.question_id = sub_counts.question_id
                    GROUP BY cq.lesson_id
                ) stats ON l.lesson_id = stats.lesson_id
                WHERE e.user_id = ? AND e.course_id = ?
                GROUP BY
                    l.lesson_id,
                    l.course_id,
                    l.title,
                    l.description,
                    l.order_index,
                    l.category,
                    l.difficult,
                    stats.total_submissions,
                    stats.accepted_submissions
                ORDER BY l.order_index ASC;
                """;
        return executeQuery(sql, rs -> {
            Lesson lesson = new Lesson();
            lesson.setLessonId(rs.getInt("lesson_id"));
            lesson.setCourseId(rs.getInt("course_id"));
            lesson.setTitle(rs.getString("title"));
            lesson.setDescription(rs.getString("description"));
            lesson.setOrderIndex(rs.getInt("order_index"));
            lesson.setCategory(rs.getString("category"));
            lesson.setDifficulty(rs.getString("difficult"));
            lesson.setAcpt(rs.getInt("ac"));
            lesson.setAcptPercent(rs.getDouble("ac_percent"));
            lesson.setStatus(rs.getString("status"));
            return lesson;
        }, userId, courseId);
    }


    public List<Submission> getCourseSubmissionHistory(int userId, int courseId) {
        String sql = """
                            SELECT
                                s.submission_id,
                                s.status,
                                s.score,
                                s.submit_time,
                                s.language,
                                s.test_passed,
                                s.total_test,
                                s.message
                            FROM submissions AS s
                            JOIN enrollments AS e ON e.enrollment_id = s.enrollment_id
                            WHERE e.user_id = ? AND e.course_id = ?
                            ORDER BY submit_time DESC;
                """;
        return executeQuery(sql, rs -> {
            Submission sub = new Submission();
            sub.setSubmissionId(rs.getInt("submission_id"));
            sub.setStatus(rs.getString("status"));
            sub.setScore(rs.getDouble("score"));
            sub.setSubmitTime(rs.getTimestamp("submit_time").toLocalDateTime());
            sub.setLanguage(rs.getString("language"));
            sub.setPassedTest(rs.getInt("test_passed"));
            sub.setTotalTest(rs.getInt("total_test"));
            sub.setMessage(rs.getString("message"));
            return sub;
        }, userId, courseId);
    }

    public List<Submission> getQuestionSubmissionHistory(int userId, int courseId, int questionId) {
        String sql = """
                    SELECT
                        s.submission_id,
                        s.status,
                        s.score,
                        s.submit_time,
                        s.language,
                        s.test_passed,
                        s.total_test,
                        s.message
                    FROM submissions AS s
                    JOIN enrollments AS e ON e.enrollment_id = s.enrollment_id
                    WHERE e.user_id = ? 
                      AND e.course_id = ?
                      AND s.question_id = ?
                    ORDER BY s.submit_time DESC;
                """;
        return executeQuery(sql, rs -> {
            Submission sub = new Submission();
            sub.setSubmissionId(rs.getInt("submission_id"));
            sub.setStatus(rs.getString("status"));
            sub.setScore(rs.getDouble("score"));
            sub.setSubmitTime(rs.getTimestamp("submit_time").toLocalDateTime());
            sub.setLanguage(rs.getString("language"));
            sub.setPassedTest(rs.getInt("test_passed"));
            sub.setTotalTest(rs.getInt("total_test"));
            sub.setMessage(rs.getString("message"));
            return sub;
        }, userId, courseId, questionId);
    }

    /**
     * Tính toán tiến độ dựa trên lịch sử submission
     * Progress = (Số bài Accepted / Tổng số bài) * 100
     *
     * @return progress theo % (0-100)
     */
    public double calculateProgress(int enrollmentId) {
        String sql = """
                    WITH course_info AS (
                        SELECT course_id 
                        FROM enrollments 
                        WHERE enrollment_id = ?
                    ),
                    total_questions AS (
                        SELECT COUNT(DISTINCT q.question_id) as total
                        FROM coding_questions q
                        JOIN lessons l ON q.lesson_id = l.lesson_id
                        JOIN course_info ci ON l.course_id = ci.course_id
                    ),
                    accepted_questions AS (
                        SELECT COUNT(DISTINCT s.question_id) as accepted
                        FROM submissions s
                        WHERE s.enrollment_id = ? AND s.status = 'Accepted'
                    )
                    SELECT 
                        COALESCE(aq.accepted, 0) as accepted_count,
                        COALESCE(tq.total, 0) as total_count
                    FROM total_questions tq
                    CROSS JOIN accepted_questions aq
                """;

        List<Double> result = executeQuery(sql, rs -> {
            int acceptedCount = rs.getInt("accepted_count");
            int totalCount = rs.getInt("total_count");

            if (totalCount == 0) {
                return 0.0;
            }

            return Math.round((acceptedCount * 100.0 / totalCount) * 100.0) / 100.0;
        }, enrollmentId, enrollmentId);

        return result.isEmpty() ? 0.0 : result.get(0);
    }

}
