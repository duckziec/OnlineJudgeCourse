package app;

import exception.DatabaseException;
import exception.ExistException;
import exception.ValidationException;
import model.*;
import service.*;
import utils.PasswordHash;

import java.util.List;
import java.util.Scanner;


public class OnlineGradingSystemMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean isLoggedIn = false;
    private static User currentUser = null;

    // Services injected from AppConfig
    private static UserService userService;
    private static CourseService courseService;
    private static EnrollmentService enrollmentService;
    private static AnalyticsService analyticsService;

    public static void main(String[] args) throws Exception {

        try {
            // Step 1: Initialize AppConfig (setup all dependencies)
            System.out.println("Initializing application...");
            AppConfig.initialize();

            // Step 2: Get services from AppConfig
            userService = AppConfig.getUserService();
            courseService = AppConfig.getCourseService();
            enrollmentService = AppConfig.getEnrollmentService();
            analyticsService = AppConfig.getAnalyticsService();

            System.out.println("Application started successfully!");

            // Step 3: Show a login menu
            showWelcomeMenu();

        } catch (DatabaseException e) {
            System.err.println(" Database Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println(" Unexpected Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== HÀM HIỂN THỊ MENU ====================
    private static void showWelcomeMenu() throws Exception {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   HỆ THỐNG CHẤM BÀI ONLINE             ║");
        System.out.println("╚════════════════════════════════════════╝");


        while (true) {
            if (!isLoggedIn) {
                showLoginMenu();
            } else {
                // khi login xong --> thực hiện load khoá hoc
                analyticsService.reloadUserDashboard(currentUser.getUserId());
                showMainMenu();
            }
        }
    }

    // ==================== MENU ĐĂNG NHẬP ====================
    private static void showLoginMenu() {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│     ĐĂNG NHẬP / ĐĂNG KÝ         │");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│ 1. Đăng nhập                    │");
        System.out.println("│ 2. Đăng ký tài khoản mới        │");
        System.out.println("│ 3. Quên mật khẩu                │");
        System.out.println("│ 0. Thoát chương trình           │");
        System.out.println("└─────────────────────────────────┘");
        System.out.print("Chọn chức năng: ");

        int choice = getIntInput();

        switch (choice) {
            case 1:
                handleLogin();
                break;
            case 2:
                handleRegister();
                break;
            case 3:
                handleLostPassword();
                break;
            case 0:
                System.out.println("\n👋 Tạm biệt! Hẹn gặp lại!");
                // Cleanup on shutdown
                AppConfig.shutdown();
                scanner.close();
                System.out.println("\nApplication closed.");
                System.exit(0);
            default:
                System.out.println("❌ Lựa chọn không hợp lệ!");
        }
    }

    //===================== Lost Password ====================
    private static void handleLostPassword() {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│        QUÊN MẬT KHẨU            │");
        System.out.println("└─────────────────────────────────┘");

        System.out.print("Nhập email của bạn: ");
        String email = scanner.nextLine();

        try {
            boolean check = userService.sendPasswordResetCode(email);
            if (check) {
                System.out.println("\n✅ Đã gửi liên kết đặt lại mật khẩu đến email của bạn!");
                System.out.println("\n Nhập mã xác nhận từ email: ");
                String code = scanner.nextLine();
                System.out.print("Nhập mật khẩu mới (tối thiểu 8 ký tự): ");
                String newPassword = scanner.nextLine();
                System.out.print("Xác nhận mật khẩu mới: ");
                String confirmPassword = scanner.nextLine();
                if (newPassword.equals(confirmPassword) && newPassword.length() >= 8) {
                    boolean resetCheck = userService.resetPasswordWithCode(email, code, newPassword);
                    if (resetCheck) {
                        System.out.println("\n✅ Đặt lại mật khẩu thành công! Vui lòng đăng nhập lại.");
                    } else {
                        System.out.println("\n❌ Mã xác nhận không hợp lệ hoặc đã hết hạn!");
                    }
                } else {
                    System.out.println("❌ Mật khẩu xác nhận không khớp hoặc chưa đủ độ dài!");
                }
            } else {
                System.out.println("\n❌ Email không tồn tại trong hệ thống!");
            }
        } catch (Exception e) {
            System.out.println("❌ Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    // ==================== MENU CHÍNH ====================
    private static void showMainMenu() throws Exception {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  👋 Xin chào, " + currentUser.getFullName() + "!");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│         MENU CHÍNH              │");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│ 1. Xem khóa học của tôi         │");
        System.out.println("│ 2. Đăng ký khóa học mới         │");
        System.out.println("│ 3. Xem thống kê cá nhân         │");
        System.out.println("│ 4. Đổi mật khẩu                 │");
        System.out.println("│ 0. Đăng xuất                    │");
        System.out.println("└─────────────────────────────────┘");
        System.out.print("Chọn chức năng: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                showMyCoursesMenu();
                break;
            case 2:
                showEnrollCourseMenu();
                break;
            case 3:
                showStatisticsMenu();
                break;
            case 4:
                handleChangePassword();
                break;
            case 0:
                handleLogout();
                break;
            default:
                System.out.println("❌ Lựa chọn không hợp lệ!");
        }
    }

    // ==================== MENU KHÓA HỌC CỦA TÔI ====================
    private static void showMyCoursesMenu() throws Exception {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│              KHÓA HỌC CỦA TÔI                           │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        List<DashboardStats> myCourses = analyticsService.getListDashboard();

        int totalCourses = myCourses.size();

        if (myCourses.isEmpty()) {
            System.out.println("Bạn chưa đăng ký khóa học nào.");
        } else {
            for (int i = 0; i < totalCourses; i++) {
                System.out.printf((i + 1) + ". " + "%s (Tiến độ: %.0f%%, Điểm: %.0f)\n", myCourses.get(i).getCourseTitle(), myCourses.get(i).getProgress(), myCourses.get(i).getTotalScore());
            }
        }

        System.out.println("0. Quay lại menu chính");
        System.out.print("\nChọn khóa học: ");

        int choice = getIntInput();
        if (choice >= 1 && choice <= totalCourses) {
            AppConfig.getLessonService().reloadLessonsWithStatus(currentUser.getUserId(), choice);
            showCourseDetailMenu(choice);
        }

    }

    // ==================== MENU CHI TIẾT KHÓA HỌC ====================
    private static void showCourseDetailMenu(int courseId) throws Exception {
        List<DashboardStats> myCourses = analyticsService.getListDashboard();
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  📚 " + myCourses.get(courseId - 1).getCourseTitle());
        System.out.println("╚════════════════════════════════════════╝");
        System.out.printf("Tiến độ: %d/%d bài (%.0f%%)\n", myCourses.get(courseId - 1).getSolvedQuestions(), myCourses.get(courseId - 1).getTotalQuestions(), myCourses.get(courseId - 1).getProgress());
        System.out.printf("Điểm số: %.0f", myCourses.get(courseId - 1).getTotalScore());

        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│      MENU KHÓA HỌC              │");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│ 1. Xem danh sách bài tập        │");
        System.out.println("│ 2. Lọc bài tập theo category    │");
        System.out.println("│ 3. Xem bài tập chưa hoàn thành  │");
        System.out.println("│ 4. Xem bài tập đã hoàn thành    │");
        System.out.println("│ 0. Quay lại menu chính          │");
        System.out.println("└─────────────────────────────────┘");
        System.out.print("Chọn chức năng: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                showExerciseListMenu(courseId);
                break;
            case 2:
                showFilterCategoryMenu(courseId);
                break;
            case 3:
                showIncompleteExercisesMenu(courseId);
                break;
            case 4:
                showCompletedExercisesMenu(courseId);
                break;
            case 0:
                return;
            default:
                System.out.println("❌ Lựa chọn không hợp lệ!");
                showCourseDetailMenu(courseId);
        }
    }

    // ==================== MENU DANH SÁCH BÀI TẬP ====================
    private static void showExerciseListMenu(int courseId) throws Exception {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│           DANH SÁCH BÀI TẬP                             │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        AppConfig.getLessonService().printAllLessonsByCourse();
        List<Lesson> listLesson = AppConfig.getLessonService().getAllLessons();

        System.out.println("\n0. Quay lại");
        System.out.print("\nChọn bài tập: ");

        int choice = getIntInput();
        if (choice >= 1 && choice <= listLesson.size()) {
            showExerciseDetailMenu(courseId, choice - 1, "");
        } else if (choice == 0) {
            showCourseDetailMenu(courseId);
        }
    }

    // ==================== MENU LỌC THEO CATEGORY ====================
    private static void showFilterCategoryMenu(int courseId) throws Exception {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│    LỌC THEO CATEGORY            │");
        System.out.println("└─────────────────────────────────┘");

        AppConfig.getLessonService().showFilterCategoryMenu();
        List<String> list = AppConfig.getLessonService().getCategoryNames();

        System.out.println("0. Quay lại");
        System.out.print("\nChọn category: ");

        int choice = getIntInput();
        if (choice >= 1 && choice <= list.size()) {
            showFilteredExercises(courseId, list.get(choice - 1));
        } else if (choice == 0) {
            showCourseDetailMenu(courseId);
        }
    }

    // ==================== MENU BÀI TẬP ĐÃ LỌC ====================
    private static void showFilteredExercises(int courseId, String category) throws Exception {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│        BÀI TẬP THEO CATEGORY                            │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        AppConfig.getLessonService().showLessonByCategory(category);
        List<Lesson> listLesson = AppConfig.getLessonService().getLessonsByCategory(category);


        System.out.println("0. Quay lại");
        System.out.print("\nChọn bài tập: ");

        int choice = getIntInput();
        if (choice >= 1 && choice <= listLesson.size()) {
            showExerciseDetailMenu(courseId, choice - 1, category);
        } else if (choice == 0) {
            showFilterCategoryMenu(courseId);
        }
    }

    // ==================== MENU BÀI TẬP CHƯA HOÀN THÀNH ====================
    private static void showIncompleteExercisesMenu(int courseId) throws Exception {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│        BÀI TẬP CHƯA HOÀN THÀNH                          │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        AppConfig.getLessonService().showLessonNotDone();
        List<Lesson> listLesson = AppConfig.getLessonService().getLessonsNotDone();

        System.out.println("\n0. Quay lại");
        System.out.print("\nChọn bài tập: ");

        int choice = getIntInput();
        if (choice >= 1 && choice <= listLesson.size()) {
            showExerciseDetailMenu(courseId, choice - 1, "");
        } else if (choice == 0) {
            showCourseDetailMenu(courseId);
        }
    }

    // ==================== MENU BÀI TẬP ĐÃ HOÀN THÀNH ====================
    private static void showCompletedExercisesMenu(int courseId) throws Exception {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│          BÀI TẬP ĐÃ HOÀN THÀNH                          │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        AppConfig.getLessonService().showLessonDone();
        List<Lesson> lessonList = AppConfig.getLessonService().getLessonsDone();

        System.out.println("\n0. Quay lại");
        System.out.print("\nChọn bài tập để xem chi tiết: ");

        int choice = getIntInput();
        if (choice >= 1 && choice <= lessonList.size()) {
            showExerciseDetailMenu(courseId, choice - 1, "");
        } else if (choice == 0) {
            showCourseDetailMenu(courseId);
        }
    }

    // ==================== MENU CHI TIẾT BÀI TẬP ====================
    private static void showExerciseDetailMenu(int courseId, int exerciseId, String category) throws Exception {
        AppConfig.getLessonService().showExcercise(exerciseId, category);
        System.out.println(exerciseId);
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│       MENU BÀI TẬP              │");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│ 1. Xem đề bài chi tiết          │");
        System.out.println("│ 2. Nộp bài giải                 │");
        System.out.println("│ 3. Xem lịch sử nộp bài          │");
        System.out.println("│ 0. Quay lại danh sách bài       │");
        System.out.println("└─────────────────────────────────┘");
        System.out.print("Chọn chức năng: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                showExerciseDescription(courseId, exerciseId);
                break;
            case 2:
                handleSubmitSolution(courseId, exerciseId);
                break;
            case 3:
                showSubmissionHistoryMenu(courseId, exerciseId);
                break;
            case 0:
                showExerciseListMenu(courseId);
                return;
            default:
                System.out.println("❌ Lựa chọn không hợp lệ!");
                showExerciseDetailMenu(courseId, exerciseId, category);
        }
    }

    // ==================== MENU ĐỀ BÀI ====================
    private static void showExerciseDescription(int courseId, int exerciseId) throws Exception {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    ĐỀ BÀI CHI TIẾT                         ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        int lessonId = AppConfig.getLessonService().getAllLessons().get(exerciseId).getLessonId();
        AppConfig.getLessonService().showExcerciseDetails(lessonId);

        System.out.println("\nNhấn Enter để quay lại...");
        scanner.nextLine();
        showExerciseDetailMenu(courseId, exerciseId, "");
    }

    // ==================== MENU NỘP BÀI ====================
    private static void handleSubmitSolution(int courseId, int exerciseId) throws Exception {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│       NỘP BÀI GIẢI              │");
        System.out.println("└─────────────────────────────────┘");
        System.out.println("Nhập code của bạn (nhập 'END' ở dòng cuối để kết thúc):\n");

        StringBuilder code = new StringBuilder();
        String line;
        while (!(line = scanner.nextLine()).equals("END")) {
            code.append(line).append("\n");
        }

        System.out.println("========Lựa chọn ngôn ngữ (python/c/cpp/java/c#/js/pascal/sql): ");
        String language = scanner.nextLine();

        Submission submission = new Submission();
        submission.setCode(code.toString());
        submission.setLanguage(language);
        CodingQuestion codingQuestion = AppConfig.getLessonService().getCodingQuestionById(AppConfig.getLessonService().getAllLessons().get(exerciseId).getLessonId());
        submission.setQuestionId(codingQuestion.getQuestionId());
        submission.setEnrollmentId(AppConfig.getEnrollmentService().getEnrollmentById(currentUser.getUserId(), courseId).getEnrollmentId());
        submission.setScore(codingQuestion.getDifficulty().equals("Dễ") ? 10.0 : codingQuestion.getDifficulty().equals("Trung bình") ? 20.0 : 30.0);

        System.out.println("\n🔄 Đang chấm bài...");
        showGradingResultMenu(courseId, exerciseId, submission);
    }

    // ==================== MENU KẾT QUẢ CHẤM BÀI ====================
    private static void showGradingResultMenu(int courseId, int exerciseId, Submission submission) throws Exception {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                  KẾT QUẢ CHẤM BÀI                          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        Judge0Service.judgeSubmission(submission);
        analyticsService.reloadUserDashboard(currentUser.getUserId());

        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│ 1. Làm lại bài tập              │");
        System.out.println("│ 2. Về danh sách bài tập         │");
        System.out.println("│ 3. Tiếp tục bài tập tiếp theo   │");
        System.out.println("│ 0. Về menu chính                │");
        System.out.println("└─────────────────────────────────┘");
        System.out.print("Chọn: ");

        int choice = getIntInput();
        switch (choice) {
            case 1:
                handleSubmitSolution(courseId, exerciseId);
                break;
            case 2:
                showExerciseListMenu(courseId);
                break;
            case 3:
                showExerciseDetailMenu(courseId, exerciseId + 1, "");
                break;
            case 0:
                showExerciseListMenu(courseId);
                break;
            default:
                System.out.println("❌ Lựa chọn không hợp lệ!");
        }
    }

    // ==================== MENU LỊCH SỬ NỘP BÀI ====================
    private static void showSubmissionHistoryMenu(int courseId, int exerciseId) throws Exception {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│           LỊCH SỬ NỘP BÀI                               │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        AppConfig.getAnalyticsService().showCourseSubmissionHistory(currentUser.getUserId(), courseId);

        System.out.println("\n0. Quay lại");
        System.out.println("\nNhấn Enter để quay lại...");
        scanner.nextLine();
        showExerciseDetailMenu(courseId, exerciseId, "");
    }

    // ==================== MENU ĐĂNG KÝ KHÓA HỌC ====================
    private static void showEnrollCourseMenu() {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│            ĐĂNG KÝ KHÓA HỌC MỚI                         │");
        System.out.println("└─────────────────────────────────────────────────────────┘");

        System.out.println("1. Lập trình Java cơ bản");
        System.out.println("   Cấu trúc ngôn ngữ, biến, điều kiện, vòng lặp, và nhập xuất dữ liệu");
        System.out.println("   Độ khó: Dễ | 35 bài tập");

        System.out.println("\n2. Lập trình OOP cùng Java");
        System.out.println("   Lập trình hướng đối tượng, kế thừa, đa hình, đóng gói");
        System.out.println("   Độ khó: Trung bình | 45 bài tập");

        System.out.println("\n3. Lập trình Python");
        System.out.println("   Xử lý dữ liệu, lập trình hướng đối tượng, thư viện phổ biến");
        System.out.println("   Độ khó: Dễ | 40 bài tập");

        System.out.println("\n4. Lập trình C");
        System.out.println("   Lập trình cơ bản, con trỏ, cấp phát bộ nhớ, file I/O");
        System.out.println("   Độ khó: Trung bình | 50 bài tập");

        System.out.println("\n5. Lập trình C++ cơ bản");
        System.out.println("   Cấu trúc dữ liệu, hàm, lớp, đối tượng, và STL cơ bản");
        System.out.println("   Độ khó: Trung bình | 55 bài tập");

        System.out.println("\n6. Lập trình C++ nâng cao");
        System.out.println("   Mẫu (template), lập trình tổng quát, xử lý ngoại lệ, đa luồng, và tối ưu hiệu năng");
        System.out.println("   Độ khó: Khó | 65 bài tập");

        System.out.println("\n7. Lập trình DSA (Thuật toán & Cấu trúc dữ liệu)");
        System.out.println("   Mảng, danh sách, ngăn xếp, hàng đợi, cây, đồ thị, thuật toán tìm kiếm & sắp xếp");
        System.out.println("   Độ khó: Khó | 70 bài tập");

        System.out.println("\n8. Lập trình Pascal");
        System.out.println("   Cấu trúc điều khiển, hàm, thủ tục, và kiểu dữ liệu cơ bản");
        System.out.println("   Độ khó: Dễ | 35 bài tập");

        System.out.println("\n9. Lập trình SQL");
        System.out.println("   Thiết kế cơ sở dữ liệu, câu lệnh SELECT, JOIN, và tối ưu truy vấn");
        System.out.println("   Độ khó: Trung bình | 45 bài tập");

        System.out.println("\n10. Lập trình C#");
        System.out.println("   Lập trình hướng đối tượng, Windows Form, LINQ và .NET Framework");
        System.out.println("   Độ khó: Trung bình | 50 bài tập");

        System.out.println("\n0. Quay lại");
        System.out.print("\nChọn khóa học để đăng ký: ");

        int choice = getIntInput();
        try {
            if (choice >= 1 && choice <= 10) {
                boolean check = enrollmentService.enrollCourse(currentUser.getUserId(), choice);
                if (check) {
                    System.out.println("\nĐăng ký khóa học thành công!");
                    analyticsService.reloadUserDashboard(currentUser.getUserId());
                } else {
                    System.out.println("\nĐăng ký khóa học thất bại!");
                }
                System.out.println("Nhấn Enter để tiếp tục...");
                scanner.nextLine();
            }
        } catch (ExistException e) {
            System.out.println(e.getMessage());
        }


    }

    // ==================== MENU THỐNG KÊ ====================
    private static void showStatisticsMenu() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║              THỐNG KÊ CÁ NHÂN                              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");

        analyticsService.showUserDashboard(currentUser.getUserId());

        System.out.println("\nNhấn Enter để quay lại...");
        scanner.nextLine();
    }

    // ==================== XỬ LÝ ĐĂNG NHẬP ====================
    private static void handleLogin() {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│         ĐĂNG NHẬP               │");
        System.out.println("└─────────────────────────────────┘");

        System.out.print("Username hoặc Email: ");
        String username = scanner.nextLine();
        System.out.print("Mật khẩu: ");
        String password = scanner.nextLine();

        try {
            currentUser = userService.login(username, password);

            if (currentUser != null) {
                System.out.println("\n Đăng nhập thành công!");
                isLoggedIn = true;
            } else {
                System.out.println("\n Đăng nhập thất bại! Vui lòng kiểm tra lại thông tin.");
            }
        } catch (Exception e) {
            System.err.println("\n❌ Đã xảy ra lỗi trong quá trình đăng nhập: " + e.getMessage());
            // In ra stack trace để giúp bạn tìm lỗi trong các lớp service/DAO
            e.printStackTrace();
            System.out.println("Vui lòng thử lại.");
        }
    }


    // ==================== XỬ LÝ ĐĂNG KÝ ====================
    private static void handleRegister() {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│      ĐĂNG KÝ TÀI KHOẢN          │");
        System.out.println("└─────────────────────────────────┘");

        try {
            System.out.print("Fullname: ");
            String fullName = scanner.nextLine();

            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Mật khẩu (tối thiểu 8 ký tự): ");
            String password = scanner.nextLine();

            System.out.print("Xác nhận mật khẩu: ");
            String confirmPassword = scanner.nextLine();

            if (password.equals(confirmPassword)) {
                boolean check = userService.registerUser(fullName, username, email, password);
                if (check) {
                    System.out.println("\n✅ Đăng ký thành công!");
                    System.out.println("Vui lòng đăng nhập để tiếp tục.");
                } else {
                    System.out.println("Trùng Username hoặc Email!");
                }
            } else {
                System.out.println("❌ Mật khẩu xác nhận không khớp!");
            }
        } catch (ValidationException e) {
            System.out.println("❌ Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Registration failed: " + e.getMessage());
        }
    }

    // ==================== XỬ LÝ ĐỔI MẬT KHẨU ====================
    private static void handleChangePassword() {
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│         ĐỔI MẬT KHẨU            │");
        System.out.println("└─────────────────────────────────┘");

        System.out.print("Mật khẩu hiện tại: ");
        String oldPassword = scanner.nextLine();

        if (!PasswordHash.checkPassword(oldPassword, currentUser.getPassword())) {
            System.out.println("Sai mật khẩu!");
            return;
        }

        System.out.print("Mật khẩu mới (tối thiểu 8 ký tự): ");
        String newPassword = scanner.nextLine();

        System.out.print("Xác nhận mật khẩu mới: ");
        String confirmPassword = scanner.nextLine();

        if (newPassword.equals(confirmPassword) && newPassword.length() >= 8) {
            currentUser.setPassword(newPassword);
            boolean check = userService.updateUser(currentUser);
            System.out.println("\n✅ Đổi mật khẩu thành công!");
        } else {
            System.out.println("❌ Đổi mật khẩu thất bại!");
        }

        System.out.println("Nhấn Enter để tiếp tục...");
        scanner.nextLine();
    }

    // ==================== XỬ LÝ ĐĂNG XUẤT ====================
    private static void handleLogout() {
        System.out.println("\n👋 Đăng xuất thành công!");
        isLoggedIn = false;
        currentUser = null;
    }

    // ==================== HÀM HỖ TRỢ ====================
    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}