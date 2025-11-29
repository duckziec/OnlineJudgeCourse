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
}