package app;

import dao.*;
import io.github.cdimascio.dotenv.Dotenv;
import service.*;

/**
 * Application Configuration - Dependency Injection Container
 * Quản lý việc khởi tạo và cung cấp dependencies
 */
public class AppConfig {

    private static final Dotenv dotenv;

    static {
        try {
            // Load .env file từ project root
            dotenv = Dotenv.configure()
                    .ignoreIfMissing()
                    .load();
        } catch (Exception e) {
            throw new RuntimeException("Không thể tải file .env: " + e.getMessage(), e);
        }
    }

    private static volatile boolean initialized = false;
    private static final Object LOCK = new Object();

    // DAOs (Singleton instances)
    private static UserDAO userDAO;
    private static CourseDAO courseDAO;
    private static LessonDAO lessonDAO;
    private static EnrollmentDAO enrollmentDAO;
    private static CodingQuestionDAO codingQuestionDAO;
    private static TestCaseDAO testCaseDAO;
    private static SubmissionDAO submissionDAO;
    private static AnalyticsDAO analyticsDAO;

    // Services (Singleton instances)
    private static UserService userService;
    private static CourseService courseService;
    private static LessonService lessonService;
    private static EnrollmentService enrollmentService;
    private static Judge0Service judge0Service;
    private static AnalyticsService analyticsService;

    /**
     * Initialize all dependencies
     */
    public static void initialize() {
        if (initialized) return;
        synchronized (LOCK) {
            if (initialized) return;
            // Initialize DBConnection pool after properties loaded
            DBConnection.getInstance();

            // Initialize DAOs
            initializeDAOs();

            // Initialize Services with DAOs injected
            initializeServices();

            initialized = true;
        }
    }

    /**
     * Initialize all DAOs
     */
    private static void initializeDAOs() {
        userDAO = UserDAO.getInstance();
        courseDAO = CourseDAO.getInstance();
        lessonDAO = LessonDAO.getInstance();
        enrollmentDAO = EnrollmentDAO.getInstance();
        codingQuestionDAO = CodingQuestionDAO.getInstance();
        testCaseDAO = TestCaseDAO.getInstance();
        submissionDAO = SubmissionDAO.getInstance();
        analyticsDAO = AnalyticsDAO.getInstance();
    }

    /**
     * Initialize all Services with Dependency Injection
     */
    private static void initializeServices() {
        userService = new UserService(userDAO);
        courseService = new CourseService(courseDAO, enrollmentDAO);
        lessonService = new LessonService(lessonDAO);
        enrollmentService = new EnrollmentService(enrollmentDAO, courseDAO, userDAO);
        judge0Service = new Judge0Service(submissionDAO, testCaseDAO);
        analyticsService = new AnalyticsService(analyticsDAO);
    }

    // ==================== GETTERS ====================

    public static UserService getUserService() {
        return userService;
    }

    public static CourseService getCourseService() {
        return courseService;
    }

    public static LessonService getLessonService() {
        return lessonService;
    }

    public static EnrollmentService getEnrollmentService() {
        return enrollmentService;
    }

    public static Judge0Service getJudge0Service() {
        return judge0Service;
    }

    public static AnalyticsService getAnalyticsService() {
        return analyticsService;
    }

    /**
     * Cleanup resources on shutdown
     */
    public static void shutdown() {
        synchronized (LOCK) {
            DBConnection.close();
            // clear references to help GC and allow re-initialize if needed
            userDAO = null;
            courseDAO = null;
            lessonDAO = null;
            enrollmentDAO = null;
            codingQuestionDAO = null;
            testCaseDAO = null;
            submissionDAO = null;
            analyticsDAO = null;
            // clear references to services
            userService = null;
            courseService = null;
            lessonService = null;
            enrollmentService = null;
            judge0Service = null;
            analyticsService = null;
            // clear other resources
            initialized = false;
        }
    }

    // API Configuration
    public static String getApiUrl() {
        return dotenv.get("API_URL");
    }

    public static String getApiKey() {
        return dotenv.get("API_KEY");
    }

    public static String getApiHost() {
        return dotenv.get("API_HOST");
    }

    // Database Configuration
    public static String getDbUrl() {
        return dotenv.get("DB_URL");
    }

    public static String getDbUser() {
        return dotenv.get("DB_USER");
    }

    public static String getDbPassword() {
        return dotenv.get("DB_PASS");
    }

    // Email Configuration
    public static String getEmailAddress() {
        return dotenv.get("EMAIL_ADDRESS");
    }

    public static String getEmailPassword() {
        return dotenv.get("EMAIL_PASSWORD");
    }
}
