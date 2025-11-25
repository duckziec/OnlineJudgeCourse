/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui.dashboard;

import app.AppConfig;
import app.SessionManager;
import model.User;
import ui.auth.Login;
import ui.auth.Register;
import ui.blog.BodyBlog;
import ui.courses.BodyCourses;
import ui.exercises.BodyCodingExercises;
import ui.exercises.TestDataExample;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author DELL
 */
public class HomePage extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(HomePage.class.getName());

    private JButton selectedButton;

    private void highlightButton(JButton button) {
        // Đặt lại màu của tất cả nút về mặc định
        btHome.setBackground(Color.WHITE);
        btHome.setForeground(Color.BLACK);

        btCourses.setBackground(Color.WHITE);
        btCourses.setForeground(Color.BLACK);

        btBlog.setBackground(Color.WHITE); // Blog
        btBlog.setForeground(Color.BLACK);

        btCoding.setBackground(Color.WHITE); // Coding Exercises
        btCoding.setForeground(Color.BLACK);

        // Gán màu cho nút được chọn
        button.setBackground(new Color(255, 87, 34)); // cam đậm
        button.setForeground(new Color(230, 81, 0));

        // Lưu lại nút hiện đang được chọn
        selectedButton = button;
    }

    /**
     * Creates new form HomePage
     */
    public HomePage() {
        initComponents();
        setTitle("VNPT Edu - Online Courses");
        setIconImage(new ImageIcon(getClass().getResource("/Logo/pixelcut-export.png")).getImage());

        // ===== PHẦN MỚI: Thiết lập window properties =====
        setResizable(true);  // Cho phép resize window
        setMinimumSize(new java.awt.Dimension(800, 620));  // Kích thước tối thiểu
        setPreferredSize(new java.awt.Dimension(1280, 720)); // Kích thước ưu tiên
        // ================================================

        bodyScrollPane.setViewportView(new BodyHomepage());
        bodyScrollPane.getVerticalScrollBar().setUnitIncrement(20); //Tốc độ cuộn

        highlightButton(btHome);
        updateLoginStatus();

        // ===== PHẦN MỚI: Xử lý sự kiện resize =====
        setupWindowResizeListener();
        // ==========================================

        // ===== PHẦN MỚI: Căn giữa màn hình =====
        setLocationRelativeTo(null);
        // ========================================

        // ===== KIỂM TRA ĐÃ LƯU USER TRƯỚC ĐÓ CHƯA =====
        checkRememberMe();
    }

    public JScrollPane getBodyScrollPane() {
        return bodyScrollPane;
    }

    public JPanel wrapPanelCenter(JPanel contentPanel) {
        JPanel wrapper = new JPanel(new java.awt.GridBagLayout());
        wrapper.setBackground(Color.WHITE);

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = java.awt.GridBagConstraints.NORTH;  // Căn trên
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;  // Chỉ mở rộng ngang
        gbc.insets = new java.awt.Insets(0, 10, 0, 10);  // Padding trái phải 50px

        wrapper.add(contentPanel, gbc);
        return wrapper;
    }

    public void navigateToHome() {
        btHomeActionPerformed(null);

        java.awt.EventQueue.invokeLater(this::refreshLayout);
    }

    // Cập nhật trạng thái đăng nhập trên header
    public void updateLoginStatus() {
        if (SessionManager.getInstance().isLoggedIn()) {
            // Đã đăng nhập
            btRegister.setText("Logout");
            btLogin.setText(SessionManager.getInstance().getCurrentUserName());
            btLogin.setEnabled(false); // Disable nút Register khi đã login
        } else {
            // Chưa đăng nhập
            btLogin.setText("Login");
            btLogin.setEnabled(true);
            btRegister.setText("Register");
            btRegister.setEnabled(true);
        }
    }

    /**
     * Thiết lập listener để xử lý khi window được resize hoặc maximize
     */
    private void setupWindowResizeListener() {
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                // Sử dụng EventQueue để đảm bảo UI được cập nhật mượt mà
                java.awt.EventQueue.invokeLater(() -> {
                    handleWindowResize();
                });
            }
        });
    }

    /**
     * Xử lý khi window thay đổi kích thước
     */
    private void handleWindowResize() {
        // Cập nhật lại toàn bộ layout
        revalidate();
        repaint();

        // Cập nhật bodyScrollPane và content bên trong
        if (bodyScrollPane != null) {
            bodyScrollPane.revalidate();
            bodyScrollPane.repaint();

            // Cập nhật view hiện tại trong scrollpane
            java.awt.Component currentView = bodyScrollPane.getViewport().getView();
            if (currentView != null) {
                currentView.revalidate();
                currentView.repaint();
            }
        }
    }

    public void refreshLayout() {
        handleWindowResize();
    }

    public java.awt.Dimension getBodyScrollPaneSize() {
        if (bodyScrollPane != null) {
            return bodyScrollPane.getSize();
        }
        return new java.awt.Dimension(0, 0);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Header = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        btLogin = new javax.swing.JButton();
        btRegister = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        btHome = new javax.swing.JButton();
        btCourses = new javax.swing.JButton();
        btBlog = new javax.swing.JButton();
        btCoding = new javax.swing.JButton();
        bodyScrollPane = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(800, 600));

        Header.setBackground(new java.awt.Color(255, 255, 255));
        Header.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 153, 102));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo/pixelcut-export.png"))); // NOI18N
        jLabel1.setText("VNPTedu");
        jPanel6.add(jLabel1);

        Header.add(jPanel6, java.awt.BorderLayout.LINE_START);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 10));

        btLogin.setFont(new java.awt.Font("Segoe UI", 0, 14));
        btLogin.setContentAreaFilled(false);
        btLogin.setOpaque(true);
        btLogin.setBackground(Color.WHITE);// NOI18N
        btLogin.setForeground(new java.awt.Color(0, 0, 0));
        btLogin.setText("Login");
        btLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btLogin.setPreferredSize(new java.awt.Dimension(80, 27));
        btLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLoginActionPerformed(evt);
            }
        });
        jPanel7.add(btLogin);

        btRegister.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btRegister.setContentAreaFilled(false);
        btRegister.setOpaque(true);
        btRegister.setBackground(Color.WHITE);
        btRegister.setForeground(new java.awt.Color(0, 0, 0));
        btRegister.setText("Register");
        btRegister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btRegister.setPreferredSize(new java.awt.Dimension(90, 27));
        btRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRegisterActionPerformed(evt);
            }
        });
        jPanel7.add(btRegister);

        Header.add(jPanel7, java.awt.BorderLayout.LINE_END);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 12));

        btHome.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btHome.setForeground(new java.awt.Color(0, 0, 0));
        btHome.setText("Home");
        btHome.setBorderPainted(false);
        btHome.setContentAreaFilled(false);
        btHome.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btHome.setFocusPainted(false);
        btHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btHomeActionPerformed(evt);
            }
        });
        jPanel8.add(btHome);

        btCourses.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btCourses.setForeground(new java.awt.Color(0, 0, 0));
        btCourses.setText("Courses");
        btCourses.setBorderPainted(false);
        btCourses.setContentAreaFilled(false);
        btCourses.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btCourses.setFocusPainted(false);
        btCourses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCoursesActionPerformed(evt);
            }
        });
        jPanel8.add(btCourses);

        btBlog.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btBlog.setForeground(new java.awt.Color(0, 0, 0));
        btBlog.setText("Blog");
        btBlog.setBorderPainted(false);
        btBlog.setContentAreaFilled(false);
        btBlog.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btBlog.setFocusPainted(false);
        btBlog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBlogActionPerformed(evt);
            }
        });
        jPanel8.add(btBlog);

        btCoding.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btCoding.setForeground(new java.awt.Color(0, 0, 0));
        btCoding.setText("Coding Exercises ");
        btCoding.setBorderPainted(false);
        btCoding.setContentAreaFilled(false);
        btCoding.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btCoding.setFocusPainted(false);
        btCoding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCodingActionPerformed(evt);
            }
        });
        jPanel8.add(btCoding);

        Header.add(jPanel8, java.awt.BorderLayout.CENTER);

        bodyScrollPane.setBackground(new java.awt.Color(255, 255, 255));
        bodyScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(bodyScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 1228, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(bodyScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btHomeActionPerformed
        highlightButton(btHome);
        BodyHomepage homePanel = new BodyHomepage();  // tạo panel mới
        bodyScrollPane.setViewportView(wrapPanelCenter(homePanel));      // hiển thị panel trong JScrollPane
        bodyScrollPane.revalidate();                    // cập nhật lại giao diện
        bodyScrollPane.repaint();

        java.awt.EventQueue.invokeLater(() -> refreshLayout());
    }//GEN-LAST:event_btHomeActionPerformed

    private void btCoursesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCoursesActionPerformed
        highlightButton(btCourses);
        BodyCourses courses = new BodyCourses();
        courses.setParentFrame(this);
        bodyScrollPane.setViewportView(wrapPanelCenter(courses));
        bodyScrollPane.revalidate();                    // cập nhật lại giao diện
        bodyScrollPane.repaint();

        java.awt.EventQueue.invokeLater(this::refreshLayout);
    }//GEN-LAST:event_btCoursesActionPerformed

    private void btLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLoginActionPerformed
        Login loginPanel = new Login();
        loginPanel.setParentFrame(this);
        bodyScrollPane.setViewportView(wrapPanelCenter(loginPanel));
        bodyScrollPane.revalidate();
        bodyScrollPane.repaint();
    }//GEN-LAST:event_btLoginActionPerformed

    private void btBlogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBlogActionPerformed
        highlightButton(btBlog);
        BodyBlog blog = new BodyBlog();
        bodyScrollPane.setViewportView(wrapPanelCenter(blog));
        bodyScrollPane.revalidate();
        bodyScrollPane.repaint();

        java.awt.EventQueue.invokeLater(this::refreshLayout);
    }//GEN-LAST:event_btBlogActionPerformed

    private void btCodingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCodingActionPerformed
        highlightButton(btCoding);
        BodyCodingExercises coding = new BodyCodingExercises();
        coding.setParentFrame(this);
        // THÊM DÒNG NÀY: Tự động load dữ liệu test
        TestDataExample.addSampleData(coding);
        bodyScrollPane.setViewportView(wrapPanelCenter(coding));
        bodyScrollPane.revalidate();                    // cập nhật lại giao diện
        bodyScrollPane.repaint();

        java.awt.EventQueue.invokeLater(this::refreshLayout);
    }//GEN-LAST:event_btCodingActionPerformed

    private void btRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRegisterActionPerformed
        if (SessionManager.getInstance().isLoggedIn()) {
            // Đăng xuất
            int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn đăng xuất?",
                    "Xác nhận",
                    javax.swing.JOptionPane.YES_NO_OPTION);

            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                SessionManager.getInstance().logout();
                updateLoginStatus();
                navigateToHome();
            }
        } else {
            Register registerPanel = new Register();
            registerPanel.setParentFrame(this);
            bodyScrollPane.setViewportView(wrapPanelCenter(registerPanel));
            bodyScrollPane.revalidate();
            bodyScrollPane.repaint();
        }
    }//GEN-LAST:event_btRegisterActionPerformed

    public void highlightCoursesButton() {
        highlightButton(btCourses);
    }

    public void highlightHomeButton() {
        highlightButton(btHome);
    }

    // shutdown logic khi đóng ứng dụng
    @Override
    public void setDefaultCloseOperation(int operation) {
        if (operation != DO_NOTHING_ON_CLOSE &&
                operation != HIDE_ON_CLOSE &&
                operation != DISPOSE_ON_CLOSE &&
                operation != EXIT_ON_CLOSE) {
            throw new IllegalArgumentException("defaultCloseOperation must be one of the allowed types");
        }

        // Nếu là EXIT_ON_CLOSE thì gắn shutdown logic
        if (operation == EXIT_ON_CLOSE) {

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    System.err.println("Running AppConfig.shutdown() ...");
                    AppConfig.shutdown();
                    System.exit(0); // đảm bảo thoát hẳn sau khi shutdown
                }
            });
        }

        // vẫn gọi hành vi gốc
        super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    // kiểm tra user đã lưu trước đó chưa
    private void checkRememberMe() {
        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(ui.auth.Login.class);
        boolean rememberMe = prefs.getBoolean("rememberMe", false);

        if (rememberMe) {
            String username = prefs.get("username", null);
            if (username != null && !username.isEmpty()) {
                try {
                    User user = AppConfig.getUserService().getUserByUsernameOrEmail(username);
                    if (user != null) {
                        SessionManager.getInstance().login(user);
                        updateLoginStatus(); // Update UI to show logged-in user
                        navigateToHome();    // Navigate away from the login screen
                    }
                } catch (Exception e) {
                    System.err.println("Auto-login failed: " + e.getMessage());
                    // If auto-login fails, do nothing and let the user log in manually.
                }
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Header;
    private javax.swing.JScrollPane bodyScrollPane;
    private javax.swing.JButton btBlog;
    private javax.swing.JButton btCoding;
    private javax.swing.JButton btCourses;
    private javax.swing.JButton btHome;
    private javax.swing.JButton btLogin;
    private javax.swing.JButton btRegister;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    // End of variables declaration//GEN-END:variables
}
