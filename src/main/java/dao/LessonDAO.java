package dao;

import model.Lesson;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LessonDAO extends BaseDAO<Lesson> implements RowMapper<Lesson> {

    private static LessonDAO lessonDAO;

    private LessonDAO() {
    }

    public static LessonDAO getInstance() {
        if (lessonDAO == null) {
            lessonDAO = new LessonDAO();
        }
        return lessonDAO;
    }

    @Override
    public Lesson mapRow(ResultSet rs) throws SQLException {
        Lesson lesson = new Lesson();
        lesson.setLessonId(rs.getInt("lesson_id"));
        lesson.setCourseId(rs.getInt("course_id"));
        lesson.setTitle(rs.getString("title"));
        lesson.setDescription(rs.getString("description"));
        lesson.setOrderIndex(rs.getInt("order_index"));
        lesson.setCategory(rs.getString("category"));
        lesson.setDifficulty(rs.getString("difficult"));
        return lesson;
    }

    // Thêm bài học (bao gồm difficulty)
    public boolean addLesson(Lesson lesson) {
        String sql = "INSERT INTO lessons (course_id, title, description, order_index, category, difficult) VALUES (?, ?, ?, ?, ?, ?)";
        return executeUpdate(sql,
                lesson.getCourseId(),
                lesson.getTitle(),
                lesson.getDescription(),
                lesson.getOrderIndex(),
                lesson.getCategory(),
                lesson.getDifficulty());
    }

    // Cập nhật bài học
    public boolean updateLesson(Lesson lesson) {
        String sql = "UPDATE lessons SET course_id=?, title=?, description=?, order_index=?, category=?, difficult=? WHERE lesson_id=?";
        return executeUpdate(sql,
                lesson.getCourseId(),
                lesson.getTitle(),
                lesson.getDescription(),
                lesson.getOrderIndex(),
                lesson.getCategory(),
                lesson.getDifficulty(),
                lesson.getLessonId()); // lesson_id phải ở cuối
    }

    // Xoá bài học
    public boolean deleteLesson(int lessonId) {
        String sql = "DELETE FROM lessons WHERE lesson_id=?";
        return executeUpdate(sql, lessonId);
    }

    //  BUG: Không có cột "lesson_name" trong DB
    // Nếu cần tìm theo title thì sửa thành:
    public Lesson getLessonByTitle(String title) {
        String sql = "SELECT * FROM lessons WHERE title=?";
        return executeQuerySingle(sql, this, title);
    }

    // Lấy toàn bộ danh sách
    public List<Lesson> getAllLessons() {
        String sql = "SELECT * FROM lessons ORDER BY order_index";
        return executeQuery(sql, this);
    }

    // Tìm theo category
    public List<Lesson> getAllLessonsByCategory(String category) {
        String sql = "SELECT * FROM lessons WHERE category=? ORDER BY order_index";
        return executeQuery(sql, this, category);
    }

    // Lấy lessons theo course
    public List<Lesson> getLessonsByCourse(int courseId) {
        String sql = "SELECT * FROM lessons WHERE course_id=? ORDER BY order_index";
        return executeQuery(sql, this, courseId);
    }

    // Lấy lesson theo ID
    public Lesson getLessonById(int lessonId) {
        String sql = "SELECT * FROM lessons WHERE lesson_id=?";
        return executeQuerySingle(sql, this, lessonId);
    }

    // get Lesson with ac and %ac
    public List<Lesson> getLessonsWithAcByCourse(int courseId) {
        String sql = """
                WITH SubmissionStats AS (
                    -- Thống kê tổng và số lượng accepted submission cho mỗi câu hỏi
                    SELECT
                        question_id,
                        COUNT(submission_id) AS total_submissions,
                        SUM(CASE WHEN status = 'Accepted' THEN 1 ELSE 0 END) AS accepted_submissions
                    FROM
                        submissions
                    GROUP BY
                        question_id
                ),
                LessonStats AS (
                    -- Tổng hợp thống kê theo từng bài học
                    SELECT
                        cq.lesson_id,
                        SUM(ss.total_submissions) AS total_submissions,
                        SUM(ss.accepted_submissions) AS accepted_submissions
                    FROM
                        coding_questions cq
                    JOIN
                        SubmissionStats ss ON cq.question_id = ss.question_id
                    GROUP BY
                        cq.lesson_id
                )
                SELECT
                    l.*,
                    COALESCE(ls.accepted_submissions, 0) AS ac,
                    CASE
                        WHEN COALESCE(ls.total_submissions, 0) > 0
                        THEN (CAST(COALESCE(ls.accepted_submissions, 0) AS REAL) * 100 / ls.total_submissions)
                        ELSE 0
                    END AS ac_percent
                FROM
                    lessons l
                LEFT JOIN
                    LessonStats ls ON l.lesson_id = ls.lesson_id
                WHERE
                    l.course_id = ?
                ORDER BY
                    l.order_index;
                """;

        return executeQuery(sql, rs -> {
            Lesson lesson = new Lesson();
            lesson.setLessonId(rs.getInt("lesson_id"));
            lesson.setCourseId(rs.getInt("course_id"));
            lesson.setTitle(rs.getString("title"));
            lesson.setDescription(rs.getString("description"));
            lesson.setOrderIndex(rs.getInt("order_index"));
            lesson.setCategory(rs.getString("category"));
            lesson.setDifficulty(rs.getString("difficulty")); // chú ý đúng tên cột
            lesson.setAcpt(rs.getInt("ac"));
            lesson.setAcptPercent(rs.getDouble("ac_percent"));
            return lesson;
        }, courseId);
    }

}