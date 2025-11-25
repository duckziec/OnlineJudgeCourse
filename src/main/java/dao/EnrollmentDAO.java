package dao;

import model.Enrollment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EnrollmentDAO extends BaseDAO implements RowMapper<Enrollment> {

    private static EnrollmentDAO enrollmentDAO;

    private EnrollmentDAO() {
    }

    public static EnrollmentDAO getInstance() {
        if (enrollmentDAO == null) {
            enrollmentDAO = new EnrollmentDAO();
        }
        return enrollmentDAO;
    }


    @Override
    public Enrollment mapRow(ResultSet rs) throws SQLException {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentId(rs.getInt("enrollment_id"));
        enrollment.setUserId(rs.getInt("user_id"));
        enrollment.setCourseId(rs.getInt("course_id"));
        enrollment.setEnrolledAt(rs.getTimestamp("enrolled_at").toLocalDateTime());
        return enrollment;
    }

    // tạo enrollment
    public boolean addEnrollment(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments (user_id, course_id, enrolled_at) VALUES (?, ?, ?)";
        return executeUpdate(sql, enrollment.getUserId(), enrollment.getCourseId(), enrollment.getEnrolledAt());
    }

    // cập nhật enrollment by user_id
    public boolean updateEnrollmentByUserID(Enrollment enrollment) {
        String sql = "UPDATE enrollments SET course_id = ? WHERE user_id = ?";
        return executeUpdate(sql, enrollment.getCourseId(), enrollment.getUserId());
    }

    // Lấy thông tin tiến độ của một enrollment
    public Double getProgressByEnrollment(int enrollmentId) {
        String sql = "SELECT progress FROM enrollments WHERE enrollment_id = ?";
        List<Double> result = executeQuery(sql, rs -> rs.getDouble("progress"), enrollmentId);
        return result.isEmpty() ? 0.0 : result.get(0);
    }

    // xoá enrollment
    public boolean deleteEnrollmentByUserID(Enrollment enrollment) {
        String sql = "DELETE FROM enrollments WHERE user_id = ?";
        return executeUpdate(sql, enrollment.getUserId());
    }

    // xoá enrollment theo id
    public boolean deleteEnrollment(int userID, int courseId) {
        String sql = "DELETE FROM enrollments WHERE user_id = ? AND course_id = ?";
        return executeUpdate(sql, userID, courseId);
    }

    // lấy ra tất cả phiếu đăng ký
    public Enrollment getEnrollmentByUserAndCourseId(int userID, int courseID) {
        String sql = "SELECT * FROM enrollments WHERE user_id = ?  AND course_id = ?";
        List<Enrollment> list = executeQuery(sql, this, userID, courseID);
        return list.isEmpty() ? null : list.get(0);
    }

    // kiểm tra user đã đăng ký khoá học chưa
    public boolean exists(int userId, int courseId) {
        String sql = "SELECT * FROM enrollments WHERE user_id = ? AND course_id = ?";
        List<Enrollment> list = executeQuery(sql, this, userId, courseId);
        return !list.isEmpty();
    }

    public List<Enrollment> getUserIdsByCourse(int courseId) {
        String sql = "SELECT * FROM enrollments WHERE course_id = ?";
        return executeQuery(sql, this, courseId);
    }

}
