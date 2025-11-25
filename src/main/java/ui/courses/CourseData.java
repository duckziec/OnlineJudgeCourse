package ui.courses;

import app.AppConfig;
import app.SessionManager;
import model.Course;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CourseData {
    private static List<Course> courses;
    private static List<Course> userCourses;

    static {
        initHardcodedData();
    }

    private static void initHardcodedData() {
        courses = AppConfig.getCourseService().getAllCourses();
        courses.get(0).setContent("Nội dung chi tiết khóa học Java:\n\n" +
                "1. Cú pháp Java cơ bản\n" +
                "2. OOP trong Java\n" +
                "3. Collections Framework\n" +
                "4. Exception Handling\n" +
                "5. File I/O và Stream");
        courses.get(1).setContent("Nội dung chi tiết khóa học OOP:\n\n" +
                "1. Encapsulation\n" +
                "2. Inheritance\n" +
                "3. Polymorphism\n" +
                "4. Abstraction\n" +
                "5. Design Patterns");
        courses.get(2).setContent("Nội dung chi tiết khóa học Python:\n\n" +
                "1. Cú pháp Python cơ bản\n" +
                "2. Data Structures\n" +
                "3. Functions và Modules\n" +
                "4. File I/O\n" +
                "5. Thư viện phổ biến");
        courses.get(3).setContent("Nội dung chi tiết khóa học C:\n\n" +
                "1. Cú pháp cơ bản C\n" +
                "2. Con trỏ (Pointers)\n" +
                "3. Quản lý bộ nhớ\n" +
                "4. Cấu trúc và Union\n" +
                "5. File I/O");
        courses.get(4).setContent("Nội dung chi tiết khóa học C++ cơ bản:\n\n" +
                "1. Giới thiệu về C++\n" +
                "2. Biến và kiểu dữ liệu\n" +
                "3. Các toán tử\n" +
                "4. Cấu trúc điều khiển\n" +
                "5. Hàm và thủ tục");
        courses.get(5).setContent("Nội dung chi tiết khóa học C++ nâng cao:\n\n" +
                "1. Lập trình hướng đối tượng\n" +
                "2. Template và Generic Programming\n" +
                "3. STL (Standard Template Library)\n" +
                "4. Exception Handling\n" +
                "5. Design Patterns");
        courses.get(6).setContent("Nội dung chi tiết khóa học DSA:\n\n" +
                "1. Array và Linked List\n" +
                "2. Stack và Queue\n" +
                "3. Tree và Graph\n" +
                "4. Thuật toán sắp xếp\n" +
                "5. Thuật toán tìm kiếm");
        courses.get(7).setContent("Nội dung chi tiết khóa học Pascal:\n\n" +
                "1. Cú pháp Pascal\n" +
                "2. Kiểu dữ liệu\n" +
                "3. Cấu trúc điều khiển\n" +
                "4. Mảng và Record\n" +
                "5. Thủ tục và Hàm");
        courses.get(8).setContent("Nội dung chi tiết khóa học SQL:\n\n" +
                "1. SELECT và WHERE\n" +
                "2. JOIN các bảng\n" +
                "3. Subquery\n" +
                "4. Index và Optimization\n" +
                "5. Transaction và ACID");
        courses.get(9).setContent("Nội dung chi tiết khóa học C#:\n\n" +
                "1. Cú pháp C# cơ bản\n" +
                "2. OOP trong C#\n" +
                "3. .NET Framework\n" +
                "4. Windows Forms\n" +
                "5. ASP.NET");
    }

    public static List<Course> getUserCourses() {
        return userCourses;
    }

    public static void reloadUserCourses() {
        userCourses = AppConfig.getCourseService().getCourseByUser(SessionManager.getInstance().getCurrentUser().getUserId());
    }

    public static void removeAllUserCourse() {
        userCourses.clear();
    }

    public static List<Course> getAllCourses() {
        return courses;
    }

    public static Course getCourseById(int id) {
        for (Course course : courses) {
            if (course.getCourseId() == id) {
                return course;
            }
        }
        return null;
    }

    public static List<Course> searchCourses(String keyword) {
        List<Course> result = new ArrayList<>();
        String searchTerm = keyword.toLowerCase().trim();

        for (Course course : courses) {
            if (course.getTitle().toLowerCase().contains(searchTerm)) {
                result.add(course);
            }
        }
        return result;
    }

    public static String getImagePath(int courseId) {
        return switch (courseId) {
            case 5 -> "/images610x360/Lập trình C++ cơ bản.png";
            case 6 -> "/images610x360/Lập trình C++ nâng cao.png";
            case 4 -> "/images610x360/Lập trình C.png";
            case 7 -> "/images610x360/Lập trình DSA.png";
            case 1 -> "/images610x360/Lập trình java cơ bản.jpg";
            case 2 -> "/images610x360/Lập trình OOP cùng Java.png";
            case 8 -> "/images610x360/Lập trình Pascal.png";
            case 9 -> "/images610x360/Lập trình SQL.png";
            case 3 -> "/images610x360/Lập trình python.png";
            case 10 -> "/images610x360/Lập trình C#.png";
            default -> "/images610x360/OIP (1).jpg";
        };
    }

    public static String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return "N/A";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateTime.format(formatter);
    }

    public static void updateFromDatabase(List<Course> newCourses) {
        if (newCourses != null && !newCourses.isEmpty()) {
            courses = new ArrayList<>(newCourses);
        }
    }

    public static void addCourse(Course course) {
        courses.add(course);
    }

    public static boolean updateCourse(Course updatedCourse) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseId() == updatedCourse.getCourseId()) {
                courses.set(i, updatedCourse);
                return true;
            }
        }
        return false;
    }

    public static boolean deleteCourse(int courseId) {
        return courses.removeIf(course -> course.getCourseId() == courseId);
    }
}