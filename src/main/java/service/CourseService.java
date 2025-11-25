package service;

import dao.CourseDAO;
import dao.EnrollmentDAO;
import model.Course;

import java.time.LocalDateTime;
import java.util.List;

public class CourseService {
    private final CourseDAO courseDAO;
    private final EnrollmentDAO enrollmentDAO;

    public CourseService(CourseDAO courseDAO, EnrollmentDAO enrollmentDAO) {
        this.courseDAO = courseDAO;
        this.enrollmentDAO = enrollmentDAO;
    }

    /**
     * Thêm khóa học mới
     */
    public boolean addCourse(String title, String description) {
        if (title == null || title.isEmpty()) {
            System.out.println("Tên khóa học không được để trống!");
            return false;
        }

        Course c = new Course();
        c.setTitle(title);
        c.setDescription(description);
        c.setCreatedAt(LocalDateTime.now());

        boolean success = courseDAO.addCourse(c);
        if (success) {
            System.out.println("Thêm khóa học thành công!");
        } else {
            System.out.println("Thêm khóa học thất bại!");
        }
        return success;
    }

    /**
     * Cập nhật thông tin khóa học
     */
    public boolean updateCourse(int courseId, String title, String description) {
        Course existing = courseDAO.findCourseById(courseId);
        if (existing == null) {
            System.out.println("Không tìm thấy khóa học có ID = " + courseId);
            return false;
        }

        existing.setTitle(title);
        existing.setDescription(description);

        boolean success = courseDAO.updateCourse(existing);
        if (success) {
            System.out.println("Cập nhật khóa học thành công!");
        } else {
            System.out.println("Cập nhật khóa học thất bại!");
        }
        return success;
    }

    /**
     * Xóa khóa học theo ID
     */
    public boolean deleteCourse(int courseId) {
        boolean success = courseDAO.deleteCourse(courseId);
        if (success) {
            System.out.println("Xóa khóa học thành công!");
        } else {
            System.out.println("Không tìm thấy khóa học để xóa!");
        }
        return success;
    }

    /**
     * Lấy danh sách toàn bộ khóa học
     */
    public List<Course> getAllCourses() {
        return courseDAO.findAllCourses();
    }

    /**
     * Tìm khóa học theo ID
     */
    public Course getCourseById(int courseID) {
        return courseDAO.getCourseById(courseID);
    }

    /**
     * Tìm khóa học theo tên (gần đúng)
     */
    public List<Course> searchCourseByName(String keyword) {
        return courseDAO.searchCourseByName(keyword);
    }

    /**
     * Lấy danh sách khóa học mà người dùng đã đăng ký
     */
    public List<Course> getCourseByUser(int userId) {
        return courseDAO.getCoursesByUser(userId);
    }
}
