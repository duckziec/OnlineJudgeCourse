package dao;

import model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CourseDAO extends BaseDAO<Course> implements RowMapper<Course> {

    private static CourseDAO courseDAO;

    private CourseDAO() {
    }

    public static CourseDAO getInstance() {
        if (courseDAO == null) {
            courseDAO = new CourseDAO();
        }
        return courseDAO;
    }

    @Override
    public Course mapRow(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setCourseId(rs.getInt("course_id"));
        course.setTitle(rs.getString("title"));
        course.setDescription(rs.getString("description"));
        course.setCreatedAt(rs.getTimestamp("create_at").toLocalDateTime());
        course.setPrice(rs.getInt("price"));
        return course;
    }

    // Thêm Course
    public boolean addCourse(Course course) {
        String sql = "INSERT INTO courses (course_id, title, description, price) VALUES (?, ?, ?, ?)";
        return executeUpdate(sql, course.getCourseId(), course.getTitle(), course.getDescription(), course.getPrice());
    }

    // Cập nhật Course
    public boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET title = ?, description = ?, price = ? WHERE course_id = ?";
        return executeUpdate(sql, course.getTitle(), course.getDescription(), course.getCourseId(), course.getPrice());
    }

    // xoá course
    public boolean deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        return executeUpdate(sql, courseId);
    }

    //tìm theo course_id
    public Course findCourseById(int courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        List<Course> list = executeQuery(sql, this, courseId);
        return list.isEmpty() ? null : list.get(0);
    }

    //lấy toan bộ ds course
    public List<Course> findAllCourses() {
        String sql = "SELECT * FROM courses";
        List<Course> list = executeQuery(sql, this);
        return list;
    }

    public Course getCourseById(int id) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        List<Course> list = executeQuery(sql, this, id);
        return list.isEmpty() ? null : list.get(0);
    }

    public List<Course> searchCourseByName(String keyword) {
        String sql = "SELECT * FROM courses WHERE title LIKE ?";
        List<Course> list = executeQuery(sql, this, keyword);
        return list;
    }

    // lấy tất cả khoá học của user
    public List<Course> getCoursesByUser(int userId) {
        String sql = """
                    SELECT c.* FROM courses c
                    JOIN enrollments e ON c.course_id = e.course_id
                    WHERE e.user_id = ?
                """;
        return executeQuery(sql, this, userId);
    }

}
