package service;

import dao.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseService {

    // Kiểm tra xem có kết nối được đến DB không
    public boolean testConnection() {
        try (Connection conn = DBConnection.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Kết nối CSDL thành công!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("❌ Không thể kết nối CSDL: " + e.getMessage());
        }
        return false;
    }

    // Tự động tạo bảng nếu chưa có
    public void initializeSchema() {
        String userTable = """
                    CREATE TABLE IF NOT EXISTS users (
                        user_id INT AUTO_INCREMENT PRIMARY KEY,
                        full_name VARCHAR(100) NOT NULL,
                        user_name VARCHAR(50) UNIQUE NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        created_at DATETIME DEFAULT CURRENT_TIMESTAMP
                    );
                """;

        String courseTable = """
                    CREATE TABLE IF NOT EXISTS courses (
                        course_id INT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(200) NOT NULL,
                        language ENUM('python','cpp','java') NOT NULL,
                        description TEXT,
                        created_at DATETIME DEFAULT CURRENT_TIMESTAMP
                    );
                """;

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(userTable);
            stmt.executeUpdate(courseTable);
            System.out.println("📘 Bảng dữ liệu đã được khởi tạo (nếu chưa có).");
        } catch (SQLException e) {
            System.err.println("⚠️ Lỗi khi khởi tạo bảng: " + e.getMessage());
        }
    }

    // Dọn dẹp DB nếu cần (ví dụ reset cho test)
    public void resetDatabase() {
        String dropUsers = "DROP TABLE IF EXISTS users;";
        String dropCourses = "DROP TABLE IF EXISTS courses;";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(dropUsers);
            stmt.executeUpdate(dropCourses);
            System.out.println("🧹 Đã xóa toàn bộ bảng trong CSDL.");
        } catch (SQLException e) {
            System.err.println("⚠️ Lỗi khi reset database: " + e.getMessage());
        }
    }
}
