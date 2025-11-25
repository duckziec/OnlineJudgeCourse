package app;

import exception.DatabaseException;
import ui.dashboard.HomePage;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Khởi tạo cấu hình & dependency
            System.out.println("Initializing AppConfig...");
            AppConfig.initialize();

            // Chạy UI (trên Event Dispatch Thread)
            SwingUtilities.invokeLater(() -> {
                System.out.println("Launching UI...");
                new HomePage().setVisible(true);
            });

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
}
