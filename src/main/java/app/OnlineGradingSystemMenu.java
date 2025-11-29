package app;

import exception.DatabaseException;
import exception.ExistException;
import exception.ValidationException;
import model.Course;
import model.User;

import javax.swing.*;
import java.util.List;
import java.util.Scanner;


public class OnlineGradingSystemMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean isLoggedIn = false;
    private static User currentUser = null;

    public static void main(String[] args) {

        try {
            // Khởi tạo cấu hình & dependency
            System.out.println("Initializing AppConfig...");
            AppConfig.initialize();
            showWelcomeMenu();

        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(null,
                    "Lỗi kết nối cơ sở dữ liệu:\n" + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Đã xảy ra lỗi:\n" + e.getMessage(),
                    "Unexpected Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    // ==================== HÀM HIỂN THỊ MENU ====================
    private static void showWelcomeMenu() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║   HỆ THỐNG CHẤM BÀI ONLINE             ║");
        System.out.println("╚════════════════════════════════════════╝");


        while (true) {
            if (!isLoggedIn) {
                showLoginMenu();
            } else {
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
            boolean check = AppConfig.getUserService().sendPasswordResetCode(email);
            if (check) {
                System.out.println("\n✅ Đã gửi liên kết đặt lại mật khẩu đến email của bạn!");
                System.out.println("\n Nhập mã xác nhận từ email: ");
                String code = scanner.nextLine();
                System.out.print("Nhập mật khẩu mới (tối thiểu 8 ký tự): ");
                String newPassword = scanner.nextLine();
                System.out.print("Xác nhận mật khẩu mới: ");
                String confirmPassword = scanner.nextLine();
                if (newPassword.equals(confirmPassword) && newPassword.length() >= 8) {
                    boolean resetCheck = AppConfig.getUserService().resetPasswordWithCode(email, code, newPassword);
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
    private static void showMainMenu() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║  👋 Xin chào, " + currentUser.getFullName() + "!");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("\n┌─────────────────────────────────┐");
        System.out.println("│         MENU CHÍNH              │");
        System.out.println("├─────────────────────────────────┤");
        System.out.println("│ 1. Xem khóa học của tôi         │");
        System.out.println("│ 2. Đăng ký khóa học mới         │");
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
            case 0:
                handleLogout();
                break;
            default:
                System.out.println("❌ Lựa chọn không hợp lệ!");
        }
    }

    // ==================== MENU KHÓA HỌC CỦA TÔI ====================
    private static void showMyCoursesMenu() {
        System.out.println("\n┌─────────────────────────────────────────────────────────┐");
        System.out.println("│              KHÓA HỌC CỦA TÔI                           │");
        System.out.println("└─────────────────────────────────────────────────────────┘");
        List<Course> listCourses = AppConfig.getCourseService().getCourseByUser(currentUser.getUserId());
        for (int i = 0; i < listCourses.size(); i++) {
            System.out.println((i + 1) + ". " + listCourses.get(i).getTitle());
        }
        System.out.println("0. Quay lại menu chính");
        scanner.nextLine();
        showMainMenu();
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
                boolean check = AppConfig.getEnrollmentService().enrollCourse(currentUser.getUserId(), choice);
                if (check) {
                    System.out.println("\nĐăng ký khóa học thành công!");
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
            currentUser = AppConfig.getUserService().login(username, password);

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
                boolean check = AppConfig.getUserService().registerUser(fullName, username, email, password);
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