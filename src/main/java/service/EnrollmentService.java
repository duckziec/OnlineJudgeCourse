package service;

import dao.CourseDAO;
import dao.EnrollmentDAO;
import dao.UserDAO;
import exception.ExistException;
import model.Course;
import model.Enrollment;
import model.User;

import java.time.LocalDateTime;
import java.util.List;

public class EnrollmentService {
    private static EnrollmentDAO enrollmentDAO;
    private final CourseDAO courseDAO;
    private final UserDAO userDAO;

    public EnrollmentService(EnrollmentDAO enrollmentDAO, CourseDAO courseDAO, UserDAO userDAO) {
        EnrollmentService.enrollmentDAO = enrollmentDAO;
        this.courseDAO = courseDAO;
        this.userDAO = userDAO;
    }

    /**
     * Đăng ký khóa học cho người dùng
     */
    public boolean enrollCourse(int userId, int courseId) {
        // Kiểm tra nếu đã đăng ký khóa này
        if (enrollmentDAO.exists(userId, courseId)) {
            throw new ExistException("Bạn đã đăng ký khoá học này rồi");
        }

        Enrollment e = new Enrollment();
        e.setUserId(userId);
        e.setCourseId(courseId);
        e.setEnrolledAt(LocalDateTime.now());

        boolean success = enrollmentDAO.addEnrollment(e);
        if (success) {
            System.out.println("Đăng ký khóa học thành công!");
        } else {
            System.out.println("Đăng ký thất bại!");
        }
        return success;
    }

    /**
     * Hủy đăng ký khóa học
     */
    public boolean cancelEnrollment(int userId, int courseId) {
        boolean success = enrollmentDAO.deleteEnrollment(userId, courseId);
        if (success) {
            System.out.println("🗑️ Hủy đăng ký khóa học thành công!");
        } else {
            System.out.println("⚠️ Không tìm thấy đăng ký để hủy!");
        }
        return success;
    }

    /**
     * Lấy danh sách học viên của một khóa học
     */
    public List<User> getUsersByCourse(int courseId) {
        return userDAO.getUsersByCourse(courseId);
    }

    /**
     * Lấy tất cả khoá học của một học viên
     */
    public List<Course> getCoursesByUser(int userId) {
        return courseDAO.getCoursesByUser(userId);
    }

    /**
     * Kiểm tra người dùng đã đăng ký khóa học chưa
     */
    public boolean isEnrolled(int userId, int courseId) {
        return enrollmentDAO.exists(userId, courseId);
    }

    // get enrollment by course
    public Enrollment getEnrollmentById(int userId, int courseId) {
        return enrollmentDAO.getEnrollmentByUserAndCourseId(userId, courseId);
    }

}
