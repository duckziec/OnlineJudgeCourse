package dao;

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

    public List<Lesson> getLessonsWithAcAndStatus(int userId, int courseId) {
        // Query tối ưu: Chia nhỏ các bảng thống kê (Subquery) rồi mới JOIN lại.
        // Cách này tránh lỗi ONLY_FULL_GROUP_BY của TiDB và tránh nhân đôi dữ liệu (Fan-out).
        String sql = """
                SELECT
                    l.lesson_id,
                    l.course_id,
                    l.title,
                    l.description,
                    l.order_index,
                    l.difficult,
                    COALESCE(cat.name, 'Chưa phân loại') AS category_name,
                
                            -- thống kê (AC và tổng submit toàn hệ thống)
                    COALESCE(g_stats.accepted_subs, 0) AS ac,
                    COALESCE(g_stats.total_subs, 0) AS total_global_subs,
                
                            -- số liệu để tính Status của user
                    COALESCE(q_counts.total_questions, 0) AS total_questions,
                    COALESCE(u_stats.user_passed_count, 0) AS user_passed_count
                
                FROM lessons l
                
                            -- 1. join bảng Categories 
                LEFT JOIN categories cat ON l.category_id = cat.category_id
                
                            -- 2. subquery: đếm tổng số câu hỏi trong mỗi bài học
                LEFT JOIN (
                    SELECT lesson_id, COUNT(question_id) AS total_questions
                    FROM coding_questions
                    GROUP BY lesson_id
                ) q_counts ON l.lesson_id = q_counts.lesson_id
                
                            -- 3. Subquery: thống kê global (tính AC và tổng submit)
                LEFT JOIN (
                    SELECT 
                        cq.lesson_id,
                        COUNT(s.submission_id) AS total_subs,
                        SUM(CASE WHEN s.status = 'Accepted' THEN 1 ELSE 0 END) AS accepted_subs
                    FROM coding_questions cq
                    JOIN submissions s ON cq.question_id = s.question_id
                    GROUP BY cq.lesson_id
                ) g_stats ON l.lesson_id = g_stats.lesson_id
                
                            -- 4. subquery: thống kê user (chỉ đếm các câu user đã làm đúng)
                LEFT JOIN (
                    SELECT 
                        cq.lesson_id,
                        COUNT(DISTINCT cq.question_id) AS user_passed_count
                    FROM coding_questions cq
                    JOIN submissions s ON cq.question_id = s.question_id
                    JOIN enrollments e ON s.enrollment_id = e.enrollment_id
                    WHERE e.user_id = ? AND s.status = 'Accepted'
                    GROUP BY cq.lesson_id
                ) u_stats ON l.lesson_id = u_stats.lesson_id
                
                WHERE l.course_id = ?
                ORDER BY l.order_index ASC;
                """;

        return executeQuery(sql, rs -> {
            Lesson lesson = new Lesson();
            lesson.setLessonId(rs.getInt("lesson_id"));
            lesson.setCourseId(rs.getInt("course_id"));
            lesson.setTitle(rs.getString("title"));
            lesson.setDescription(rs.getString("description"));
            lesson.setOrderIndex(rs.getInt("order_index"));

            // Map cột category_name
            lesson.setCategory(rs.getString("category_name"));
            lesson.setDifficulty(rs.getString("difficult"));

            // 1. Xử lý AC và AC percent
            long ac = rs.getLong("ac");
            long totalGlobal = rs.getLong("total_global_subs");
            double acPercent = (totalGlobal > 0) ? ((double) ac * 100.0 / totalGlobal) : 0.0;

            lesson.setAcpt((int) ac);
            lesson.setAcptPercent(acPercent);

            // 2. Xử lý Status (User)
            int totalQuestions = rs.getInt("total_questions");
            int userPassed = rs.getInt("user_passed_count");

            String status;
            if (totalQuestions == 0) {
                status = "Chưa có bài tập";
            } else if (totalQuestions == userPassed) {
                status = "Done";
            } else {
                status = "NotDone";
            }
            lesson.setStatus(status);

            return lesson;
        }, userId, courseId); // tham số: userId (cho subquery 4), courseId (cho WHERE)
    }

    public List<Submission> getQuestionSubmissionHistory(int userId, int courseId, int questionId) {
        String sql = """
                SELECT
                    s.submission_id,
                    s.status,
                    s.submit_time,
                    s.language,
                    s.message,
                
                    -- 1. Lấy độ khó từ bảng coding_questions
                    cq.difficult,
                
                    -- 2. Đếm tổng số test case từ bảng test_cases
                    (SELECT COUNT(*) FROM test_cases t WHERE t.question_id = s.question_id) AS total_test,
                
                    -- 3. test passed từ submissions
                    s.test_passed AS test_passed
                
                FROM submissions AS s
                         JOIN enrollments AS e ON e.enrollment_id = s.enrollment_id
                         JOIN coding_questions AS cq ON s.question_id = cq.question_id
                
                WHERE e.user_id = ?
                  AND e.course_id = ?
                  AND s.question_id = ?
                ORDER BY s.submit_time DESC;
                """;

        return executeQuery(sql, rs -> {
            Submission sub = new Submission();
            sub.setSubmissionId(rs.getInt("submission_id"));
            sub.setStatus(rs.getString("status"));
            sub.setSubmitTime(rs.getTimestamp("submit_time").toLocalDateTime());
            sub.setLanguage(rs.getString("language"));
            sub.setMessage(rs.getString("message"));

            // Lấy dữ liệu thô từ việc JOIN
            int totalTest = rs.getInt("total_test");
            int testPassed = rs.getInt("test_passed");
            String difficulty = rs.getString("difficult");

            sub.setTotalTest(totalTest);
            sub.setPassedTest(testPassed);

            // --- TÍNH ĐIỂM (Logic Java) ---
            // Ưu điểm: Bạn chỉ cần sửa logic ở đây nếu muốn đổi thang điểm, không cần sửa DB
            double maxScore = switch (difficulty) {
                case "Dễ" -> 10.0;
                case "Trung Bình" -> 20.0;
                case "Khó" -> 30.0;
                default -> 0.0;
            };

            // Công thức: (Số test đúng / Tổng test) * Điểm Max
            double actualScore = 0.0;
            if (totalTest > 0) {
                actualScore = (double) testPassed / totalTest * maxScore;
            }

            // Làm tròn (ví dụ 10/30 * 10 = 3.33)
            sub.setScore(Math.round(actualScore * 100.0) / 100.0);

            return sub;
        }, userId, courseId, questionId);
    }

    /**
     * Tính toán tiến độ dựa trên lịch sử submission
     * Progress = (Số bài Accepted / Tổng số bài) * 100
     *
     * @return progress theo % (0-100)
     */
    public double calculateProgress(int userId, int courseId) {
        String sql = """
                SELECT 
                            -- 1. Mẫu số: Tổng số câu hỏi trong khoá học
                    COUNT(DISTINCT cq.question_id) AS total_count,
                
                            -- 2. Tử số: Số câu hỏi user đã làm đúng (Accepted)
                    COUNT(DISTINCT CASE 
                        WHEN s.status = 'Accepted' THEN cq.question_id 
                    END) AS accepted_count
                
                FROM lessons l
                        -- Lấy tất cả câu hỏi thuộc khoá học này
                JOIN coding_questions cq ON l.lesson_id = cq.lesson_id
                
                        -- Tìm thông tin ghi danh của User trong khoá học này (để lấy enrollment_id)
                LEFT JOIN enrollments e ON l.course_id = e.course_id AND e.user_id = ?
                
                        -- Join với bảng nộp bài dựa trên enrollment tìm được
                LEFT JOIN submissions s ON s.enrollment_id = e.enrollment_id 
                                       AND s.question_id = cq.question_id
                
                WHERE l.course_id = ?;
                """;

        List<Double> result = executeQuery(sql, rs -> {
            int totalCount = rs.getInt("total_count");
            int acceptedCount = rs.getInt("accepted_count");

            // Tránh lỗi chia cho 0 nếu khoá học chưa có bài tập nào
            if (totalCount == 0) return 0.0;

            // làm tròn 2 chữ số thập phân
            return Math.round((acceptedCount * 100.0 / totalCount) * 100.0) / 100.0;
        }, userId, courseId); // tham số: userId trước, courseId sau

        return result.isEmpty() ? 0.0 : result.get(0);
    }

}
