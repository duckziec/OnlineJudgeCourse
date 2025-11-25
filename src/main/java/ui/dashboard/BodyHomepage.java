/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.dashboard;

import app.AppConfig;
import model.Course;
import model.User;
import ui.courses.BodyCourses;
import ui.courses.CourseData;
import ui.courses.CourseSingle;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author DELL
 */
public class BodyHomepage extends javax.swing.JPanel {

    private List<Course> allCourses;
    private static List<User> allUsers = AppConfig.getUserService().getAllUsers();
    private HomePage parentFrame;

    /**
     * Creates new form BodyHomepage
     */
    public BodyHomepage() {
        initComponents();
        loadData();
    }

    public HomePage getParentHomePage() {
        java.awt.Container container = this.getParent();
        while (container != null && !(container instanceof HomePage)) {
            container = container.getParent();
        }
        return (HomePage) container;
    }

    private void navigateToCourses() {
        HomePage homePage = getParentHomePage();
        if (homePage != null) {
            homePage.highlightCoursesButton();

            BodyCourses courses = new BodyCourses();
            courses.setParentFrame(homePage);
            homePage.getBodyScrollPane().setViewportView(
                    homePage.wrapPanelCenter(courses)
            );
            homePage.getBodyScrollPane().revalidate();
            homePage.getBodyScrollPane().repaint();
        }
    }

    private void loadData() {
        // Load tất cả khóa học
        allCourses = CourseData.getAllCourses();

        // Load Categories
        loadCategories();

        // Load Featured Courses (6 khóa học đầu tiên)
        loadFeaturedCourses();

        // Load Statistics
        loadStatistics();

        // Load Latest Articles (3 khóa học free cuối)
        loadLatestArticles();
    }

    private void loadCategories() {
        // Đếm số khóa học theo từng ngôn ngữ
        int cppCount = countCoursesByLanguage("C++");
        int javaCount = countCoursesByLanguage("Java");
        int pythonCount = countCoursesByLanguage("Python");
        int csharpCount = countCoursesByLanguage("C#");
        int sqlCount = countCoursesByLanguage("SQL");

        // Cập nhật label và text field
        lbMon1.setText("C++");
        txtTong1.setText(cppCount + " Courses");

        lbMon2.setText("Java");
        txtTong2.setText(javaCount + " Courses");

        lbMon3.setText("Python");
        txtTong3.setText(pythonCount + " Courses");

        lbMon4.setText("C#");
        txtTong4.setText(csharpCount + " Courses");

        lbMon5.setText("SQL");
        txtTong5.setText(sqlCount + " Courses");
    }

    private int countCoursesByLanguage(String language) {
        int count = 0;
        for (Course course : allCourses) {
            if (course.getTitle().contains(language)) {
                count++;
            }
        }
        return count;
    }

    private void loadFeaturedCourses() {
        // Load 6 khóa học đầu tiên
        for (int i = 0; i < Math.min(6, allCourses.size()); i++) {
            Course course = allCourses.get(i);

            switch (i) {
                case 0:
                    loadCourseToUI(course, lbPicture1, lbTitle1, lbGia1, 1);
                    break;
                case 1:
                    loadCourseToUI(course, lbPicture2, lbTitle2, lbGia2, 2);
                    break;
                case 2:
                    loadCourseToUI(course, lbPicture3, lbTitle3, lbGia3, 3);
                    break;
                case 3:
                    loadCourseToUI(course, lbPicture4, lbTitle4, lbGia4, 4);
                    break;
                case 4:
                    loadCourseToUI(course, lbPicture5, lbTitle5, lbGia5, 5);
                    break;
                case 5:
                    loadCourseToUI(course, lbPicture6, lbTitle6, lbGia6, 6);
                    break;
            }
        }
    }

    private void loadCourseToUI(Course course, javax.swing.JLabel lbPicture,
                                javax.swing.JLabel lbTitle, javax.swing.JLabel lbGia, int index) {
        // Cập nhật tiêu đề
        lbTitle.setText(course.getTitle());
        lbTitle.putClientProperty("courseId", course.getCourseId());
        lbTitle.putClientProperty("courseIndex", index);

        // Cập nhật giá
        lbGia.setText(course.getPrice() == 0 ? "Free" : course.getPrice() + " VND");

        // Load hình ảnh
        String imagePath = CourseData.getImagePath(course.getCourseId());
        imagePath = imagePath.replace("/images610x360/", "/images/");
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            if (icon.getIconWidth() > 0) {
                lbPicture.setIcon(icon);
            }
        } catch (Exception e) {
            System.err.println("Không thể load hình: " + imagePath);
        }
    }

    private void loadStatistics() {
        // Đếm tổng số users
        int totalUsers = allUsers.size();
        lbActiveStudent.setText(totalUsers + "K+");

        // Đếm tổng số khóa học
        lbTotal.setText(allCourses.size() + "+");

        // Đếm tổng số bài tập (giả sử có 10 bài)
        lbTotalExercises.setText("250+");

    }

    private void loadLatestArticles() {
        // Load 3 khóa học free cuối cùng
        List<Course> freeCourses = getFreeCourses();

        for (int i = 0; i < Math.min(3, freeCourses.size()); i++) {
            Course course = freeCourses.get(freeCourses.size() - 3 + i);

            switch (i) {
                case 0 -> loadArticleToUI(course, lbPicture7, lbTitle7, txtDescription1, 7);
                case 1 -> loadArticleToUI(course, lbPicture8, lbTitle8, txtDescription2, 8);
                case 2 -> loadArticleToUI(course, lbPicture9, lbTitle9, txtDescription3, 9);
            }
        }
    }

    private List<Course> getFreeCourses() {
        List<Course> freeCourses = new java.util.ArrayList<>();
        for (Course course : allCourses) {
            if (course.getPrice() == 0) {
                freeCourses.add(course);
            }
        }
        return freeCourses;
    }

    private void loadArticleToUI(Course course, javax.swing.JLabel lbPicture,
                                 javax.swing.JLabel lbTitle, javax.swing.JTextArea txtDescription,
                                 int index) {
        // Cập nhật tiêu đề
        lbTitle.setText(course.getTitle());
        lbTitle.putClientProperty("courseId", course.getCourseId());
        lbTitle.putClientProperty("courseIndex", index);

        // Cập nhật mô tả
        txtDescription.setText(course.getDescription());
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setEditable(false);

        // Load hình ảnh
        String imagePath = CourseData.getImagePath(course.getCourseId());
        imagePath = imagePath.replace("/images610x360/", "/images/");
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            if (icon.getIconWidth() > 0) {
                lbPicture.setIcon(icon);
            }
        } catch (Exception e) {
            System.err.println("Không thể load hình: " + imagePath);
        }
    }

    private void openCourseSingle(int courseId) {
        Course course = CourseData.getCourseById(courseId);
        if (course != null) {
            HomePage homePage = getParentHomePage();
            if (homePage != null) {
                homePage.highlightCoursesButton();

                CourseSingle courseSingle = new CourseSingle();
                courseSingle.loadCourseData(course);
                courseSingle.setParentFrame(homePage);

                homePage.getBodyScrollPane().setViewportView(
                        homePage.wrapPanelCenter(courseSingle)
                );
                homePage.getBodyScrollPane().revalidate();
                homePage.getBodyScrollPane().repaint();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel39 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        lbPicture7 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        lbTitle7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription1 = new javax.swing.JTextArea();
        btViewMore7 = new javax.swing.JButton();
        jPanel40 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        lbPicture8 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        lbTitle8 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescription2 = new javax.swing.JTextArea();
        btViewMore8 = new javax.swing.JButton();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        lbPicture9 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        lbTitle9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescription3 = new javax.swing.JTextArea();
        btViewMore9 = new javax.swing.JButton();
        btAllCourse2 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        lbActiveStudent = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        lbTotal = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        lbTotalExercises = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        lbPicture1 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        lbTitle1 = new javax.swing.JLabel();
        lbGia1 = new javax.swing.JLabel();
        btViewMore1 = new javax.swing.JButton();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        lbPicture2 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        lbTitle2 = new javax.swing.JLabel();
        lbGia2 = new javax.swing.JLabel();
        btViewMore2 = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        lbPicture3 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        lbTitle3 = new javax.swing.JLabel();
        lbGia3 = new javax.swing.JLabel();
        btViewMore3 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        lbPicture4 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        lbTitle4 = new javax.swing.JLabel();
        lbGia4 = new javax.swing.JLabel();
        btViewMore4 = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        lbPicture5 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        lbTitle5 = new javax.swing.JLabel();
        lbGia5 = new javax.swing.JLabel();
        btViewMore5 = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        lbPicture6 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        lbTitle6 = new javax.swing.JLabel();
        lbGia6 = new javax.swing.JLabel();
        btViewMore6 = new javax.swing.JButton();
        btAllCourse = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btAllCategories = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lbMon1 = new javax.swing.JLabel();
        txtTong1 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        lbMon2 = new javax.swing.JLabel();
        txtTong2 = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        lbMon3 = new javax.swing.JLabel();
        txtTong3 = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        lbMon4 = new javax.swing.JLabel();
        txtTong4 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        lbMon5 = new javax.swing.JLabel();
        txtTong5 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridBagLayout());

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));

        jLabel51.setBackground(new java.awt.Color(255, 255, 255));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/footer.png"))); // NOI18N

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));

        jLabel37.setBackground(new java.awt.Color(255, 255, 255));
        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 0, 0));
        jLabel37.setText("Latest Articles");

        jLabel38.setBackground(new java.awt.Color(255, 255, 255));
        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 0, 0));
        jLabel38.setText("Explore our Free Acticles");

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setLayout(new java.awt.GridLayout(1, 3, 20, 20));

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (1).jpg"))); // NOI18N
        jPanel36.add(lbPicture7);

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setRequestFocusEnabled(false);
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle7.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTitle7.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle7.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel37.add(lbTitle7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        txtDescription1.setColumns(20);
        txtDescription1.setForeground(new java.awt.Color(0, 0, 0));
        txtDescription1.setRows(5);
        txtDescription1.setText("Learn programming from scratch with simple, clear \nlessons.");
        jScrollPane1.setViewportView(txtDescription1);

        jPanel37.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 300, 70));

        btViewMore7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore7.setContentAreaFilled(false);
        btViewMore7.setOpaque(true);
        btViewMore7.setBackground(Color.WHITE);
        btViewMore7.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore7.setText("View More");
        btViewMore7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore7ActionPerformed(evt);
            }
        });
        jPanel37.add(btViewMore7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, -1, 30));

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
                jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel35Layout.setVerticalGroup(
                jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel35Layout.createSequentialGroup()
                                .addComponent(jPanel36, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jPanel34.add(jPanel35);

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (1).jpg"))); // NOI18N
        jPanel41.add(lbPicture8);

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));
        jPanel42.setRequestFocusEnabled(false);
        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle8.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTitle8.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle8.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel42.add(lbTitle8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        txtDescription2.setColumns(20);
        txtDescription2.setForeground(new java.awt.Color(0, 0, 0));
        txtDescription2.setRows(5);
        txtDescription2.setText("Learn programming from scratch with simple, clear \nlessons.");
        jScrollPane2.setViewportView(txtDescription2);

        jPanel42.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 300, 70));

        btViewMore8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore8.setContentAreaFilled(false);
        btViewMore8.setOpaque(true);
        btViewMore8.setBackground(Color.WHITE);
        btViewMore8.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore8.setText("View More");
        btViewMore8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore8ActionPerformed(evt);
            }
        });
        jPanel42.add(btViewMore8, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, -1, 30));

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
                jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel40Layout.setVerticalGroup(
                jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel40Layout.createSequentialGroup()
                                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jPanel34.add(jPanel40);

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));
        jPanel43.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (1).jpg"))); // NOI18N
        jPanel44.add(lbPicture9);

        jPanel45.setBackground(new java.awt.Color(255, 255, 255));
        jPanel45.setRequestFocusEnabled(false);
        jPanel45.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle9.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTitle9.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle9.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel45.add(lbTitle9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        txtDescription3.setColumns(20);
        txtDescription3.setForeground(new java.awt.Color(0, 0, 0));
        txtDescription3.setRows(5);
        txtDescription3.setText("Learn programming from scratch with simple, clear \nlessons.");
        jScrollPane3.setViewportView(txtDescription3);

        jPanel45.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 300, 70));

        btViewMore9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore9.setContentAreaFilled(false);
        btViewMore9.setOpaque(true);
        btViewMore9.setBackground(Color.WHITE);
        btViewMore9.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore9.setText("View More");
        btViewMore9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore9ActionPerformed(evt);
            }
        });
        jPanel45.add(btViewMore9, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, -1, 30));

        javax.swing.GroupLayout jPanel43Layout = new javax.swing.GroupLayout(jPanel43);
        jPanel43.setLayout(jPanel43Layout);
        jPanel43Layout.setHorizontalGroup(
                jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel43Layout.setVerticalGroup(
                jPanel43Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel43Layout.createSequentialGroup()
                                .addComponent(jPanel44, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel45, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jPanel34.add(jPanel43);

        btAllCourse2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btAllCourse2.setContentAreaFilled(false);
        btAllCourse2.setOpaque(true);
        btAllCourse2.setBackground(Color.WHITE);
        btAllCourse2.setForeground(new java.awt.Color(0, 0, 0));
        btAllCourse2.setText("All Courses");
        btAllCourse2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btAllCourse2.setContentAreaFilled(false);
        btAllCourse2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btAllCourse2.setFocusPainted(false);
        btAllCourse2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAllCourse2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
                jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel33Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jLabel37)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 875, Short.MAX_VALUE)
                                .addComponent(btAllCourse2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                                .addContainerGap(112, Short.MAX_VALUE)
                                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(109, 109, 109))
                        .addGroup(jPanel33Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel33Layout.setVerticalGroup(
                jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel33Layout.createSequentialGroup()
                                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel37)
                                        .addComponent(btAllCourse2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.GridLayout(1, 4, 20, 0));

        jPanel29.setBackground(new java.awt.Color(229, 229, 229));
        jPanel29.setLayout(new java.awt.GridLayout(2, 1));

        lbActiveStudent.setBackground(new java.awt.Color(255, 255, 255));
        lbActiveStudent.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbActiveStudent.setForeground(new java.awt.Color(255, 153, 102));
        lbActiveStudent.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbActiveStudent.setText("25K+");
        lbActiveStudent.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel29.add(lbActiveStudent);

        jLabel30.setBackground(new java.awt.Color(255, 255, 255));
        jLabel30.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(0, 0, 0));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Active Student");
        jPanel29.add(jLabel30);

        jPanel6.add(jPanel29);

        jPanel30.setBackground(new java.awt.Color(229, 229, 229));
        jPanel30.setLayout(new java.awt.GridLayout(2, 1));

        lbTotal.setBackground(new java.awt.Color(255, 255, 255));
        lbTotal.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbTotal.setForeground(new java.awt.Color(255, 153, 102));
        lbTotal.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTotal.setText("25K+");
        lbTotal.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel30.add(lbTotal);

        jLabel32.setBackground(new java.awt.Color(229, 229, 229));
        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 0, 0));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Total Courses");
        jPanel30.add(jLabel32);

        jPanel6.add(jPanel30);

        jPanel31.setBackground(new java.awt.Color(229, 229, 229));
        jPanel31.setLayout(new java.awt.GridLayout(2, 1));

        jLabel33.setBackground(new java.awt.Color(255, 255, 255));
        jLabel33.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 153, 102));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("1");
        jLabel33.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel31.add(jLabel33);

        jLabel34.setBackground(new java.awt.Color(255, 255, 255));
        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(0, 0, 0));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Instructor ");
        jPanel31.add(jLabel34);

        jPanel6.add(jPanel31);

        jPanel32.setBackground(new java.awt.Color(229, 229, 229));
        jPanel32.setLayout(new java.awt.GridLayout(2, 1));

        lbTotalExercises.setBackground(new java.awt.Color(255, 255, 255));
        lbTotalExercises.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbTotalExercises.setForeground(new java.awt.Color(255, 153, 102));
        lbTotalExercises.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbTotalExercises.setText("25K+");
        lbTotalExercises.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel32.add(lbTotalExercises);

        jLabel36.setBackground(new java.awt.Color(255, 255, 255));
        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(0, 0, 0));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Total Exercises");
        jPanel32.add(jLabel36);

        jPanel6.add(jPanel32);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Freatured Courses");

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 0));
        jLabel10.setText("Explore our Popular Courses");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.GridLayout(2, 3, 20, 20));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (1).jpg"))); // NOI18N
        jPanel12.add(lbPicture1);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setRequestFocusEnabled(false);
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle1.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTitle1.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle1.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel13.add(lbTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia1.setBackground(new java.awt.Color(255, 255, 255));
        lbGia1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbGia1.setForeground(new java.awt.Color(0, 0, 0));
        lbGia1.setText("Free");
        jPanel13.add(lbGia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, -1));

        btViewMore1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore1.setContentAreaFilled(false);
        btViewMore1.setOpaque(true);
        btViewMore1.setBackground(Color.WHITE);
        btViewMore1.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore1.setText("View More");
        btViewMore1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore1ActionPerformed(evt);
            }
        });
        jPanel13.add(btViewMore1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel8Layout.setVerticalGroup(
                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jPanel7.add(jPanel8);

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (4).jpg"))); // NOI18N
        jPanel15.add(lbPicture2);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setRequestFocusEnabled(false);
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle2.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTitle2.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle2.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel16.add(lbTitle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia2.setBackground(new java.awt.Color(255, 255, 255));
        lbGia2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbGia2.setForeground(new java.awt.Color(0, 0, 0));
        lbGia2.setText("Free");
        jPanel16.add(lbGia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, -1));

        btViewMore2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore2.setContentAreaFilled(false);
        btViewMore2.setOpaque(true);
        btViewMore2.setBackground(Color.WHITE);
        btViewMore2.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore2.setText("View More");
        btViewMore2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore2ActionPerformed(evt);
            }
        });
        jPanel16.add(btViewMore2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel14Layout.setVerticalGroup(
                jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel14Layout.createSequentialGroup()
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jPanel7.add(jPanel14);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (1).jpg"))); // NOI18N
        jPanel18.add(lbPicture3);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setRequestFocusEnabled(false);
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle3.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTitle3.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle3.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel19.add(lbTitle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia3.setBackground(new java.awt.Color(255, 255, 255));
        lbGia3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbGia3.setForeground(new java.awt.Color(0, 0, 0));
        lbGia3.setText("Free");
        jPanel19.add(lbGia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, -1));

        btViewMore3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore3.setContentAreaFilled(false);
        btViewMore3.setOpaque(true);
        btViewMore3.setBackground(Color.WHITE);
        btViewMore3.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore3.setText("View More");
        btViewMore3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore3ActionPerformed(evt);
            }
        });
        jPanel19.add(btViewMore3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
                jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel17Layout.setVerticalGroup(
                jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jPanel7.add(jPanel17);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (1).jpg"))); // NOI18N
        jPanel21.add(lbPicture4);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setRequestFocusEnabled(false);
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle4.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTitle4.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle4.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel22.add(lbTitle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia4.setBackground(new java.awt.Color(255, 255, 255));
        lbGia4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbGia4.setForeground(new java.awt.Color(0, 0, 0));
        lbGia4.setText("Free");
        jPanel22.add(lbGia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, -1));

        btViewMore4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore4.setContentAreaFilled(false);
        btViewMore4.setOpaque(true);
        btViewMore4.setBackground(Color.WHITE);
        btViewMore4.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore4.setText("View More");
        btViewMore4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore4ActionPerformed(evt);
            }
        });
        jPanel22.add(btViewMore4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
                jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel20Layout.setVerticalGroup(
                jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel20Layout.createSequentialGroup()
                                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jPanel7.add(jPanel20);

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (1).jpg"))); // NOI18N
        jPanel24.add(lbPicture5);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setRequestFocusEnabled(false);
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle5.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTitle5.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle5.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel25.add(lbTitle5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia5.setBackground(new java.awt.Color(255, 255, 255));
        lbGia5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbGia5.setForeground(new java.awt.Color(0, 0, 0));
        lbGia5.setText("Free");
        jPanel25.add(lbGia5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, -1));

        btViewMore5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore5.setContentAreaFilled(false);
        btViewMore5.setOpaque(true);
        btViewMore5.setBackground(Color.WHITE);
        btViewMore5.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore5.setText("View More");
        btViewMore5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore5ActionPerformed(evt);
            }
        });
        jPanel25.add(btViewMore5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
                jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel23Layout.setVerticalGroup(
                jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel23Layout.createSequentialGroup()
                                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jPanel7.add(jPanel23);

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (1).jpg"))); // NOI18N
        jPanel27.add(lbPicture6);

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setRequestFocusEnabled(false);
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle6.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lbTitle6.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle6.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel28.add(lbTitle6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia6.setBackground(new java.awt.Color(255, 255, 255));
        lbGia6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbGia6.setForeground(new java.awt.Color(0, 0, 0));
        lbGia6.setText("Free");
        jPanel28.add(lbGia6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, -1));

        btViewMore6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore6.setContentAreaFilled(false);
        btViewMore6.setOpaque(true);
        btViewMore6.setBackground(Color.WHITE);
        btViewMore6.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore6.setText("View More");
        btViewMore6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore6ActionPerformed(evt);
            }
        });
        jPanel28.add(btViewMore6, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
                jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel26Layout.setVerticalGroup(
                jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel26Layout.createSequentialGroup()
                                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        jPanel7.add(jPanel26);

        btAllCourse.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btAllCourse.setForeground(new java.awt.Color(0, 0, 0));
        btAllCourse.setText("All Courses");
        btAllCourse.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btAllCourse.setContentAreaFilled(false);
        btAllCourse.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btAllCourse.setFocusPainted(false);
        btAllCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAllCourseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addContainerGap(112, Short.MAX_VALUE)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(109, 109, 109))
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                                .addGap(19, 19, 19)
                                                .addComponent(jLabel9)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btAllCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel9)
                                        .addComponent(btAllCourse))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Top Categories");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Explore our Popular Categories");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 220, -1));

        btAllCategories.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btAllCategories.setContentAreaFilled(false);
        btAllCategories.setOpaque(true);
        btAllCategories.setBackground(Color.WHITE);
        btAllCategories.setForeground(new java.awt.Color(0, 0, 0));
        btAllCategories.setText("All Categories");
        btAllCategories.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btAllCategories.setBorderPainted(false);
        btAllCategories.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btAllCategories.setFocusPainted(false);
        btAllCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAllCategoriesActionPerformed(evt);
            }
        });
        jPanel1.add(btAllCategories, new org.netbeans.lib.awtextra.AbsoluteConstraints(1051, 30, 130, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridLayout(1, 5, 30, 30));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));
        jPanel3.setLayout(new java.awt.GridLayout(2, 1));

        lbMon1.setBackground(new java.awt.Color(255, 255, 255));
        lbMon1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbMon1.setForeground(new java.awt.Color(0, 0, 0));
        lbMon1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbMon1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo/m1.png"))); // NOI18N
        lbMon1.setText("C++");
        lbMon1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel3.add(lbMon1);

        txtTong1.setEditable(false);
        txtTong1.setBackground(new java.awt.Color(255, 255, 255));
        txtTong1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTong1.setForeground(new java.awt.Color(0, 0, 0));
        txtTong1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTong1.setText("36 Course");
        txtTong1.setBorder(null);
        txtTong1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTong1ActionPerformed(evt);
            }
        });
        jPanel3.add(txtTong1);

        jPanel2.add(jPanel3);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));
        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        lbMon2.setBackground(new java.awt.Color(255, 255, 255));
        lbMon2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbMon2.setForeground(new java.awt.Color(0, 0, 0));
        lbMon2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbMon2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo/m2.png"))); // NOI18N
        lbMon2.setText("JavaScript");
        lbMon2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel5.add(lbMon2);

        txtTong2.setEditable(false);
        txtTong2.setBackground(new java.awt.Color(255, 255, 255));
        txtTong2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTong2.setForeground(new java.awt.Color(0, 0, 0));
        txtTong2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTong2.setText("36 Course");
        txtTong2.setBorder(null);
        txtTong2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTong2ActionPerformed(evt);
            }
        });
        jPanel5.add(txtTong2);

        jPanel2.add(jPanel5);

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));
        jPanel9.setLayout(new java.awt.GridLayout(2, 1));

        lbMon3.setBackground(new java.awt.Color(255, 255, 255));
        lbMon3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbMon3.setForeground(new java.awt.Color(0, 0, 0));
        lbMon3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbMon3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo/m3.png"))); // NOI18N
        lbMon3.setText("Python");
        lbMon3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel9.add(lbMon3);

        txtTong3.setEditable(false);
        txtTong3.setBackground(new java.awt.Color(255, 255, 255));
        txtTong3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTong3.setForeground(new java.awt.Color(0, 0, 0));
        txtTong3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTong3.setText("36 Course");
        txtTong3.setBorder(null);
        jPanel9.add(txtTong3);

        jPanel2.add(jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));
        jPanel10.setLayout(new java.awt.GridLayout(2, 1));

        lbMon4.setBackground(new java.awt.Color(255, 255, 255));
        lbMon4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbMon4.setForeground(new java.awt.Color(0, 0, 0));
        lbMon4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbMon4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo/m4.png"))); // NOI18N
        lbMon4.setText("C#");
        lbMon4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel10.add(lbMon4);

        txtTong4.setEditable(false);
        txtTong4.setBackground(new java.awt.Color(255, 255, 255));
        txtTong4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTong4.setForeground(new java.awt.Color(0, 0, 0));
        txtTong4.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTong4.setText("36 Course");
        txtTong4.setBorder(null);
        jPanel10.add(txtTong4);

        jPanel2.add(jPanel10);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));
        jPanel11.setLayout(new java.awt.GridLayout(2, 1));

        lbMon5.setBackground(new java.awt.Color(255, 255, 255));
        lbMon5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lbMon5.setForeground(new java.awt.Color(0, 0, 0));
        lbMon5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbMon5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo/m5.png"))); // NOI18N
        lbMon5.setText("SQL");
        lbMon5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel11.add(lbMon5);

        txtTong5.setEditable(false);
        txtTong5.setBackground(new java.awt.Color(255, 255, 255));
        txtTong5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTong5.setForeground(new java.awt.Color(0, 0, 0));
        txtTong5.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtTong5.setText("36 Course");
        txtTong5.setBorder(null);
        jPanel11.add(txtTong5);

        jPanel2.add(jPanel11);

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 1160, 160));

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
                jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1230, Short.MAX_VALUE)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel38Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addContainerGap())
                                .addGroup(jPanel38Layout.createSequentialGroup()
                                        .addGap(120, 120, 120)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 993, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel38Layout.setVerticalGroup(
                jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1482, Short.MAX_VALUE)
                        .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel38Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/banner.png"))); // NOI18N
        jLabel3.setAlignmentY(0.1F);

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
                jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel39Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel39Layout.createSequentialGroup()
                                                        .addGap(6, 6, 6)
                                                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 1218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel39Layout.setVerticalGroup(
                jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel39Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel3)
                                        .addGap(6, 6, 6)
                                        .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))
        );

        add(jPanel39, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void btAllCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAllCategoriesActionPerformed
        navigateToCourses();
    }//GEN-LAST:event_btAllCategoriesActionPerformed

    private void txtTong1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTong1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTong1ActionPerformed

    private void txtTong2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTong2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTong2ActionPerformed

    private void btViewMore1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore1ActionPerformed
        Integer courseId = (Integer) lbTitle1.getClientProperty("courseId");
        if (courseId != null) {
            openCourseSingle(courseId);
        }
    }//GEN-LAST:event_btViewMore1ActionPerformed

    private void btViewMore2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore2ActionPerformed
        Integer courseId = (Integer) lbTitle2.getClientProperty("courseId");
        if (courseId != null) {
            openCourseSingle(courseId);
        }
    }//GEN-LAST:event_btViewMore2ActionPerformed

    private void btViewMore3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore3ActionPerformed
        Integer courseId = (Integer) lbTitle3.getClientProperty("courseId");
        if (courseId != null) {
            openCourseSingle(courseId);
        }
    }//GEN-LAST:event_btViewMore3ActionPerformed

    private void btViewMore4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore4ActionPerformed
        Integer courseId = (Integer) lbTitle4.getClientProperty("courseId");
        if (courseId != null) {
            openCourseSingle(courseId);
        }
    }//GEN-LAST:event_btViewMore4ActionPerformed

    private void btViewMore5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore5ActionPerformed
        Integer courseId = (Integer) lbTitle5.getClientProperty("courseId");
        if (courseId != null) {
            openCourseSingle(courseId);
        }
    }//GEN-LAST:event_btViewMore5ActionPerformed

    private void btViewMore6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore6ActionPerformed
        Integer courseId = (Integer) lbTitle6.getClientProperty("courseId");
        if (courseId != null) {
            openCourseSingle(courseId);
        }
    }//GEN-LAST:event_btViewMore6ActionPerformed

    private void btAllCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAllCourseActionPerformed
        navigateToCourses();
    }//GEN-LAST:event_btAllCourseActionPerformed

    private void btAllCourse2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAllCourse2ActionPerformed
        navigateToCourses();
    }//GEN-LAST:event_btAllCourse2ActionPerformed

    private void btViewMore7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore7ActionPerformed
        Integer courseId = (Integer) lbTitle7.getClientProperty("courseId");
        if (courseId != null) {
            openCourseSingle(courseId);
        }
    }//GEN-LAST:event_btViewMore7ActionPerformed

    private void btViewMore8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore8ActionPerformed
        Integer courseId = (Integer) lbTitle8.getClientProperty("courseId");
        if (courseId != null) {
            openCourseSingle(courseId);
        }
    }//GEN-LAST:event_btViewMore8ActionPerformed

    private void btViewMore9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore9ActionPerformed
        Integer courseId = (Integer) lbTitle9.getClientProperty("courseId");
        if (courseId != null) {
            openCourseSingle(courseId);
        }
    }//GEN-LAST:event_btViewMore9ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAllCategories;
    private javax.swing.JButton btAllCourse;
    private javax.swing.JButton btAllCourse2;
    private javax.swing.JButton btViewMore1;
    private javax.swing.JButton btViewMore2;
    private javax.swing.JButton btViewMore3;
    private javax.swing.JButton btViewMore4;
    private javax.swing.JButton btViewMore5;
    private javax.swing.JButton btViewMore6;
    private javax.swing.JButton btViewMore7;
    private javax.swing.JButton btViewMore8;
    private javax.swing.JButton btViewMore9;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbActiveStudent;
    private javax.swing.JLabel lbGia1;
    private javax.swing.JLabel lbGia2;
    private javax.swing.JLabel lbGia3;
    private javax.swing.JLabel lbGia4;
    private javax.swing.JLabel lbGia5;
    private javax.swing.JLabel lbGia6;
    private javax.swing.JLabel lbMon1;
    private javax.swing.JLabel lbMon2;
    private javax.swing.JLabel lbMon3;
    private javax.swing.JLabel lbMon4;
    private javax.swing.JLabel lbMon5;
    private javax.swing.JLabel lbPicture1;
    private javax.swing.JLabel lbPicture2;
    private javax.swing.JLabel lbPicture3;
    private javax.swing.JLabel lbPicture4;
    private javax.swing.JLabel lbPicture5;
    private javax.swing.JLabel lbPicture6;
    private javax.swing.JLabel lbPicture7;
    private javax.swing.JLabel lbPicture8;
    private javax.swing.JLabel lbPicture9;
    private javax.swing.JLabel lbTitle1;
    private javax.swing.JLabel lbTitle2;
    private javax.swing.JLabel lbTitle3;
    private javax.swing.JLabel lbTitle4;
    private javax.swing.JLabel lbTitle5;
    private javax.swing.JLabel lbTitle6;
    private javax.swing.JLabel lbTitle7;
    private javax.swing.JLabel lbTitle8;
    private javax.swing.JLabel lbTitle9;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JLabel lbTotalExercises;
    private javax.swing.JTextArea txtDescription1;
    private javax.swing.JTextArea txtDescription2;
    private javax.swing.JTextArea txtDescription3;
    private javax.swing.JTextField txtTong1;
    private javax.swing.JTextField txtTong2;
    private javax.swing.JTextField txtTong3;
    private javax.swing.JTextField txtTong4;
    private javax.swing.JTextField txtTong5;
    // End of variables declaration//GEN-END:variables
}
