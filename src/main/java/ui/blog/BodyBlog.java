/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.blog;

import model.Course;
import ui.courses.CourseData;
import ui.dashboard.HomePage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author DELL
 */
public class BodyBlog extends javax.swing.JPanel {

    private List<Course> courses;
    private JLabel[] titleLabels;
    private JTextArea[] contentAreas;
    private JPanel[] coursePanels;
    private JLabel[] pictureLabels;

    /**
     * Creates new form BodyBlog
     */
    public BodyBlog() {
        initComponents();
        initCourseData();
        loadCoursesToUI();
        txtTim.setText("Nhập từ khóa tìm kiếm...");
        txtTim.setForeground(Color.GRAY);

    }

    private void initCourseData() {
        courses = CourseData.getAllCourses();

        titleLabels = new JLabel[]{
                lbTitle1, lbTitle2, lbTitle3, lbTitle4, lbTitle5,
                lbTitle6, lbTitle7, lbTitle8, lbTitle9, lbTitle10
        };

        contentAreas = new JTextArea[]{
                txtContent1, txtContent2, txtContent3, txtContent4, txtContent5,
                txtContent6, txtContent7, txtContent8, txtContent9, txtContent10

        };

        pictureLabels = new JLabel[]{
                lbPicture1, lbPicture2, lbPicture3, lbPicture4, lbPicture5,
                lbPicture6, lbPicture7, lbPicture8, lbPicture9, lbPicture10,};

        coursePanels = new JPanel[]{
                jPanel57, jPanel60, jPanel64, jPanel67, jPanel71,
                jPanel74, jPanel78, jPanel81, jPanel85, jPanel88
        };
    }

    // Load dữ liệu khóa học lên UI
    private void loadCoursesToUI() {
        for (int i = 0; i < Math.min(courses.size(), 10); i++) {
            Course course = courses.get(i);
            titleLabels[i].setText(course.getTitle());
            titleLabels[i].putClientProperty("courseId", course.getCourseId());
            contentAreas[i].setText(course.getDescription());
            contentAreas[i].setLineWrap(true);
            contentAreas[i].setWrapStyleWord(true);
            contentAreas[i].setEditable(false);

            loadCourseImage(course.getCourseId(), i);
        }
    }

    private void loadCourseImage(int courseId, int index) {
        if (index < 0 || index >= pictureLabels.length) {
            return;
        }

        String imagePath = CourseData.getImagePath(courseId);
        // Đổi đường dẫn từ images610x360 sang images (dùng ảnh nhỏ hơn cho danh sách)
        imagePath = imagePath.replace("/images610x360/", "/images/");

        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));

            if (icon.getIconWidth() > 0) {
                pictureLabels[index].setIcon(icon);
                System.out.println("✓ Đã load hình: " + imagePath);
            } else {
                System.err.println("✗ Không thể load hình ảnh: " + imagePath);
            }
        } catch (Exception e) {
            System.err.println("✗ Lỗi khi load hình ảnh: " + imagePath);
            System.err.println("  Chi tiết: " + e.getMessage());
        }
    }

    // Tìm kiếm khóa học
    private void searchCourses() {
        String keyword = txtTim.getText().trim();

        // Reset màu nền tất cả panels
        resetPanelColors();

        if (keyword.isEmpty()) {
            return;
        }

        // Highlight các panel có tiêu đề khớp
        for (int i = 0; i < titleLabels.length; i++) {
            String title = titleLabels[i].getText().toLowerCase();
            if (title.contains(keyword.toLowerCase())) {
                highlightPanel(coursePanels[i]);
            }
        }
    }

    // Reset màu nền panels
    private void resetPanelColors() {
        Color defaultColor = new Color(204, 204, 204);
        for (JPanel panel : coursePanels) {
            setAllPanelColors(panel, defaultColor);
        }
    }

    private void highlightPanel(JPanel panel) {
        Color highlightColor = new Color(255, 165, 0); // Màu cam
        setAllPanelColors(panel, highlightColor);
    }

    // Đặt màu cho panel và các panel con
    private void setAllPanelColors(JPanel panel, Color color) {
        panel.setBackground(color);
        for (java.awt.Component comp : panel.getComponents()) {
            if (comp instanceof JPanel) {
                ((JPanel) comp).setBackground(color);
            }
        }
    }

    private void openBlogSingle(int index) {
        if (index < 0 || index >= courses.size()) {
            return;
        }

        Course course = courses.get(index);
        BlogSingle blogSingle = new BlogSingle();
        blogSingle.loadCourseData(course);

        // Tìm HomePage để set parent
        java.awt.Container container = this.getParent();
        while (container != null && !(container instanceof HomePage)) {
            container = container.getParent();
        }
        if (container instanceof HomePage) {
            blogSingle.setParentFrame((HomePage) container);
        }

        // Chuyển panel
        navigateToBlogSingle(blogSingle);
    }

    private void navigateToBlogSingle(BlogSingle blogSingle) {
        java.awt.Container container = this.getParent();
        while (container != null && !(container instanceof HomePage)) {
            container = container.getParent();
        }

        if (container instanceof HomePage) {
            HomePage homePage = (HomePage) container;
            homePage.getBodyScrollPane().setViewportView(homePage.wrapPanelCenter(blogSingle));
            homePage.getBodyScrollPane().revalidate();
            homePage.getBodyScrollPane().repaint();
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
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel2 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jPanel39 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        jPanel48 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jPanel49 = new javax.swing.JPanel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jPanel50 = new javax.swing.JPanel();
        jPanel51 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jButton15 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        jPanel58 = new javax.swing.JPanel();
        lbPicture1 = new javax.swing.JLabel();
        jPanel59 = new javax.swing.JPanel();
        lbTitle1 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        txtContent1 = new javax.swing.JTextArea();
        btViewMore1 = new javax.swing.JButton();
        jPanel60 = new javax.swing.JPanel();
        jPanel61 = new javax.swing.JPanel();
        lbPicture2 = new javax.swing.JLabel();
        jPanel62 = new javax.swing.JPanel();
        lbTitle2 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        txtContent2 = new javax.swing.JTextArea();
        btViewMore2 = new javax.swing.JButton();
        jPanel63 = new javax.swing.JPanel();
        jPanel64 = new javax.swing.JPanel();
        jPanel65 = new javax.swing.JPanel();
        lbPicture3 = new javax.swing.JLabel();
        jPanel66 = new javax.swing.JPanel();
        lbTitle3 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        txtContent3 = new javax.swing.JTextArea();
        btViewMore3 = new javax.swing.JButton();
        jPanel67 = new javax.swing.JPanel();
        jPanel68 = new javax.swing.JPanel();
        lbPicture4 = new javax.swing.JLabel();
        jPanel69 = new javax.swing.JPanel();
        lbTitle4 = new javax.swing.JLabel();
        jScrollPane15 = new javax.swing.JScrollPane();
        txtContent4 = new javax.swing.JTextArea();
        btViewMore4 = new javax.swing.JButton();
        jPanel70 = new javax.swing.JPanel();
        jPanel71 = new javax.swing.JPanel();
        jPanel72 = new javax.swing.JPanel();
        lbPicture5 = new javax.swing.JLabel();
        jPanel73 = new javax.swing.JPanel();
        lbTitle5 = new javax.swing.JLabel();
        jScrollPane16 = new javax.swing.JScrollPane();
        txtContent5 = new javax.swing.JTextArea();
        btViewMore5 = new javax.swing.JButton();
        jPanel74 = new javax.swing.JPanel();
        jPanel75 = new javax.swing.JPanel();
        lbPicture6 = new javax.swing.JLabel();
        jPanel76 = new javax.swing.JPanel();
        lbTitle6 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        txtContent6 = new javax.swing.JTextArea();
        btViewMore6 = new javax.swing.JButton();
        jPanel77 = new javax.swing.JPanel();
        jPanel78 = new javax.swing.JPanel();
        jPanel79 = new javax.swing.JPanel();
        lbPicture7 = new javax.swing.JLabel();
        jPanel80 = new javax.swing.JPanel();
        lbTitle7 = new javax.swing.JLabel();
        jScrollPane18 = new javax.swing.JScrollPane();
        txtContent7 = new javax.swing.JTextArea();
        btViewMore7 = new javax.swing.JButton();
        jPanel81 = new javax.swing.JPanel();
        jPanel82 = new javax.swing.JPanel();
        lbPicture8 = new javax.swing.JLabel();
        jPanel83 = new javax.swing.JPanel();
        lbTitle8 = new javax.swing.JLabel();
        jScrollPane19 = new javax.swing.JScrollPane();
        txtContent8 = new javax.swing.JTextArea();
        btViewMore8 = new javax.swing.JButton();
        jPanel84 = new javax.swing.JPanel();
        jPanel85 = new javax.swing.JPanel();
        jPanel86 = new javax.swing.JPanel();
        lbPicture9 = new javax.swing.JLabel();
        jPanel87 = new javax.swing.JPanel();
        lbTitle9 = new javax.swing.JLabel();
        jScrollPane20 = new javax.swing.JScrollPane();
        txtContent9 = new javax.swing.JTextArea();
        btViewMore9 = new javax.swing.JButton();
        jPanel88 = new javax.swing.JPanel();
        jPanel89 = new javax.swing.JPanel();
        lbPicture10 = new javax.swing.JLabel();
        jPanel90 = new javax.swing.JPanel();
        lbTitle10 = new javax.swing.JLabel();
        jScrollPane21 = new javax.swing.JScrollPane();
        txtContent10 = new javax.swing.JTextArea();
        btViewMore10 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtTim = new javax.swing.JTextField();
        btTim = new javax.swing.JButton();

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/banner.png"))); // NOI18N
        jLabel2.setText("jLabel2");

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
        jPanel34.setLayout(new java.awt.GridLayout(5, 2, 20, 20));

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));
        jPanel35.setLayout(new java.awt.GridLayout(1, 2));

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (1).jpg"))); // NOI18N
        jPanel36.add(jLabel39);

        jPanel35.add(jPanel36);

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setRequestFocusEnabled(false);
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel40.setBackground(new java.awt.Color(255, 255, 255));
        jLabel40.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 0, 0));
        jLabel40.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel37.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel41.setBackground(new java.awt.Color(255, 255, 255));
        jLabel41.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 0, 0));
        jLabel41.setText("Free");
        jPanel37.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, -1));

        jPanel35.add(jPanel37);

        jPanel34.add(jPanel35);

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));
        jPanel38.setLayout(new java.awt.GridLayout(1, 2));

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));

        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (1).jpg"))); // NOI18N
        jPanel39.add(jLabel42);

        jPanel38.add(jPanel39);

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setRequestFocusEnabled(false);
        jPanel40.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel43.setBackground(new java.awt.Color(255, 255, 255));
        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(0, 0, 0));
        jLabel43.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel40.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel44.setBackground(new java.awt.Color(255, 255, 255));
        jLabel44.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 0, 0));
        jLabel44.setText("Free");
        jPanel40.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, -1));

        jPanel38.add(jPanel40);

        jPanel34.add(jPanel38);

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));
        jPanel41.setLayout(new java.awt.GridLayout(1, 2));

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (1).jpg"))); // NOI18N
        jPanel42.add(jLabel45);

        jPanel41.add(jPanel42);

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));
        jPanel43.setRequestFocusEnabled(false);
        jPanel43.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel46.setBackground(new java.awt.Color(255, 255, 255));
        jLabel46.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(0, 0, 0));
        jLabel46.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel43.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel47.setBackground(new java.awt.Color(255, 255, 255));
        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 0, 0));
        jLabel47.setText("Free");
        jPanel43.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, -1));

        jPanel41.add(jPanel43);

        jPanel34.add(jPanel41);

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));
        jPanel44.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));
        jPanel44.setLayout(new java.awt.GridLayout(1, 2));

        jPanel45.setBackground(new java.awt.Color(255, 255, 255));

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (1).jpg"))); // NOI18N
        jPanel45.add(jLabel48);

        jPanel44.add(jPanel45);

        jPanel46.setBackground(new java.awt.Color(255, 255, 255));
        jPanel46.setRequestFocusEnabled(false);
        jPanel46.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel49.setBackground(new java.awt.Color(255, 255, 255));
        jLabel49.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(0, 0, 0));
        jLabel49.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel46.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel50.setBackground(new java.awt.Color(255, 255, 255));
        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(0, 0, 0));
        jLabel50.setText("Free");
        jPanel46.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, -1));

        jPanel44.add(jPanel46);

        jPanel34.add(jPanel44);

        jPanel47.setBackground(new java.awt.Color(255, 255, 255));
        jPanel47.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));
        jPanel47.setLayout(new java.awt.GridLayout(1, 2));

        jPanel48.setBackground(new java.awt.Color(255, 255, 255));

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (1).jpg"))); // NOI18N
        jPanel48.add(jLabel51);

        jPanel47.add(jPanel48);

        jPanel49.setBackground(new java.awt.Color(255, 255, 255));
        jPanel49.setRequestFocusEnabled(false);
        jPanel49.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel52.setBackground(new java.awt.Color(255, 255, 255));
        jLabel52.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(0, 0, 0));
        jLabel52.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel49.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel53.setBackground(new java.awt.Color(255, 255, 255));
        jLabel53.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(0, 0, 0));
        jLabel53.setText("Free");
        jPanel49.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, -1));

        jPanel47.add(jPanel49);

        jPanel34.add(jPanel47);

        jPanel50.setBackground(new java.awt.Color(255, 255, 255));
        jPanel50.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));
        jPanel50.setLayout(new java.awt.GridLayout(1, 2));

        jPanel51.setBackground(new java.awt.Color(255, 255, 255));

        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/OIP (1).jpg"))); // NOI18N
        jPanel51.add(jLabel54);

        jPanel50.add(jPanel51);

        jPanel52.setBackground(new java.awt.Color(255, 255, 255));
        jPanel52.setRequestFocusEnabled(false);
        jPanel52.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel55.setBackground(new java.awt.Color(255, 255, 255));
        jLabel55.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(0, 0, 0));
        jLabel55.setText("Cấu Trúc Dữ Liệu Và Giải Thuật");
        jPanel52.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel56.setBackground(new java.awt.Color(255, 255, 255));
        jLabel56.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 0, 0));
        jLabel56.setText("Free");
        jPanel52.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, -1));

        jPanel50.add(jPanel52);

        jPanel34.add(jPanel50);

        jButton15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jButton15.setForeground(new java.awt.Color(0, 0, 0));
        jButton15.setText("All Courses");
        jButton15.setBorderPainted(false);
        jButton15.setContentAreaFilled(false);
        jButton15.setFocusPainted(false);
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel38)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel33Layout.createSequentialGroup()
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton15)
                        .addGap(28, 28, 28))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel33Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(109, 109, 109))
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel33Layout.createSequentialGroup()
                .addGroup(jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jButton15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridBagLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setLayout(new java.awt.GridLayout(5, 2, 20, 20));

        jPanel56.setBackground(new java.awt.Color(255, 255, 255));
        jPanel56.setLayout(new java.awt.GridLayout(1, 2, 15, 0));

        jPanel57.setLayout(new java.awt.GridLayout(1, 2));

        jPanel58.setBackground(new java.awt.Color(204, 204, 204));

        lbPicture1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình C++ cơ bản.png"))); // NOI18N
        jPanel58.add(lbPicture1);

        jPanel57.add(jPanel58);

        jPanel59.setBackground(new java.awt.Color(204, 204, 204));
        jPanel59.setForeground(new java.awt.Color(0, 0, 0));
        jPanel59.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle1.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle1.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle1.setText("Lập trình C++ cơ bản");
        jPanel59.add(lbTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 250, 30));

        txtContent1.setColumns(20);
        txtContent1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtContent1.setForeground(new java.awt.Color(0, 0, 0));
        txtContent1.setRows(5);
        txtContent1.setText("asdasd");
        jScrollPane12.setViewportView(txtContent1);

        jPanel59.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 250, 100));

        btViewMore1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore1.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore1.setText("View More");
        btViewMore1.setBorderPainted(false);
        btViewMore1.setContentAreaFilled(false);
        btViewMore1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore1.setFocusPainted(false);
        btViewMore1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore1ActionPerformed(evt);
            }
        });
        jPanel59.add(btViewMore1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, -1));

        jPanel57.add(jPanel59);

        jPanel56.add(jPanel57);

        jPanel60.setBackground(new java.awt.Color(255, 255, 255));
        jPanel60.setLayout(new java.awt.GridLayout(1, 2));

        jPanel61.setBackground(new java.awt.Color(204, 204, 204));

        lbPicture2.setBackground(new java.awt.Color(255, 255, 255));
        lbPicture2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình C++ nâng cao.png"))); // NOI18N
        jPanel61.add(lbPicture2);

        jPanel60.add(jPanel61);

        jPanel62.setBackground(new java.awt.Color(204, 204, 204));
        jPanel62.setForeground(new java.awt.Color(0, 0, 0));
        jPanel62.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle2.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle2.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle2.setText("Lập trình C++ nâng cao");
        jPanel62.add(lbTitle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 250, 30));

        txtContent2.setColumns(20);
        txtContent2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtContent2.setForeground(new java.awt.Color(0, 0, 0));
        txtContent2.setRows(5);
        txtContent2.setText("asdasd");
        jScrollPane13.setViewportView(txtContent2);

        jPanel62.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 250, 100));

        btViewMore2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore2.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore2.setText("View More");
        btViewMore2.setBorderPainted(false);
        btViewMore2.setContentAreaFilled(false);
        btViewMore2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore2.setFocusPainted(false);
        btViewMore2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore2ActionPerformed(evt);
            }
        });
        jPanel62.add(btViewMore2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, -1));

        jPanel60.add(jPanel62);

        jPanel56.add(jPanel60);

        jPanel11.add(jPanel56);

        jPanel63.setBackground(new java.awt.Color(255, 255, 255));
        jPanel63.setLayout(new java.awt.GridLayout(1, 2, 15, 0));

        jPanel64.setLayout(new java.awt.GridLayout(1, 2));

        jPanel65.setBackground(new java.awt.Color(204, 204, 204));

        lbPicture3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình C.png"))); // NOI18N
        jPanel65.add(lbPicture3);

        jPanel64.add(jPanel65);

        jPanel66.setBackground(new java.awt.Color(204, 204, 204));
        jPanel66.setForeground(new java.awt.Color(0, 0, 0));
        jPanel66.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle3.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle3.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle3.setText("Lập trình C");
        jPanel66.add(lbTitle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 250, 30));

        txtContent3.setColumns(20);
        txtContent3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtContent3.setForeground(new java.awt.Color(0, 0, 0));
        txtContent3.setRows(5);
        txtContent3.setText("asdasd");
        jScrollPane14.setViewportView(txtContent3);

        jPanel66.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 250, 100));

        btViewMore3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore3.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore3.setText("View More");
        btViewMore3.setBorderPainted(false);
        btViewMore3.setContentAreaFilled(false);
        btViewMore3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore3.setFocusPainted(false);
        btViewMore3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore3ActionPerformed(evt);
            }
        });
        jPanel66.add(btViewMore3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, -1));

        jPanel64.add(jPanel66);

        jPanel63.add(jPanel64);

        jPanel67.setBackground(new java.awt.Color(255, 255, 255));
        jPanel67.setLayout(new java.awt.GridLayout(1, 2));

        jPanel68.setBackground(new java.awt.Color(204, 204, 204));

        lbPicture4.setBackground(new java.awt.Color(255, 255, 255));
        lbPicture4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình DSA.png"))); // NOI18N
        jPanel68.add(lbPicture4);

        jPanel67.add(jPanel68);

        jPanel69.setBackground(new java.awt.Color(204, 204, 204));
        jPanel69.setForeground(new java.awt.Color(0, 0, 0));
        jPanel69.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle4.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle4.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle4.setText("Cấu trúc dữ liệu và giải thuật");
        jPanel69.add(lbTitle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 250, 30));

        txtContent4.setColumns(20);
        txtContent4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtContent4.setForeground(new java.awt.Color(0, 0, 0));
        txtContent4.setRows(5);
        txtContent4.setText("asdasd");
        jScrollPane15.setViewportView(txtContent4);

        jPanel69.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 250, 100));

        btViewMore4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore4.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore4.setText("View More");
        btViewMore4.setBorderPainted(false);
        btViewMore4.setContentAreaFilled(false);
        btViewMore4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore4.setFocusPainted(false);
        btViewMore4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore4ActionPerformed(evt);
            }
        });
        jPanel69.add(btViewMore4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, -1));

        jPanel67.add(jPanel69);

        jPanel63.add(jPanel67);

        jPanel11.add(jPanel63);

        jPanel70.setBackground(new java.awt.Color(255, 255, 255));
        jPanel70.setLayout(new java.awt.GridLayout(1, 2, 15, 0));

        jPanel71.setLayout(new java.awt.GridLayout(1, 2));

        jPanel72.setBackground(new java.awt.Color(204, 204, 204));

        lbPicture5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình java cơ bản.jpg"))); // NOI18N
        jPanel72.add(lbPicture5);

        jPanel71.add(jPanel72);

        jPanel73.setBackground(new java.awt.Color(204, 204, 204));
        jPanel73.setForeground(new java.awt.Color(0, 0, 0));
        jPanel73.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle5.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle5.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle5.setText("Lập trình Java cơ bản");
        jPanel73.add(lbTitle5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 250, 30));

        txtContent5.setColumns(20);
        txtContent5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtContent5.setForeground(new java.awt.Color(0, 0, 0));
        txtContent5.setRows(5);
        txtContent5.setText("asdasd");
        jScrollPane16.setViewportView(txtContent5);

        jPanel73.add(jScrollPane16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 250, 100));

        btViewMore5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore5.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore5.setText("View More");
        btViewMore5.setBorderPainted(false);
        btViewMore5.setContentAreaFilled(false);
        btViewMore5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore5.setFocusPainted(false);
        btViewMore5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore5ActionPerformed(evt);
            }
        });
        jPanel73.add(btViewMore5, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, -1));

        jPanel71.add(jPanel73);

        jPanel70.add(jPanel71);

        jPanel74.setBackground(new java.awt.Color(255, 255, 255));
        jPanel74.setLayout(new java.awt.GridLayout(1, 2));

        jPanel75.setBackground(new java.awt.Color(204, 204, 204));

        lbPicture6.setBackground(new java.awt.Color(255, 255, 255));
        lbPicture6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình OOP cùng Java.png"))); // NOI18N
        jPanel75.add(lbPicture6);

        jPanel74.add(jPanel75);

        jPanel76.setBackground(new java.awt.Color(204, 204, 204));
        jPanel76.setForeground(new java.awt.Color(0, 0, 0));
        jPanel76.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle6.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle6.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle6.setText("Lập trình OOP");
        jPanel76.add(lbTitle6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 250, 30));

        txtContent6.setColumns(20);
        txtContent6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtContent6.setForeground(new java.awt.Color(0, 0, 0));
        txtContent6.setRows(5);
        txtContent6.setText("asdasd");
        jScrollPane17.setViewportView(txtContent6);

        jPanel76.add(jScrollPane17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 250, 100));

        btViewMore6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore6.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore6.setText("View More");
        btViewMore6.setBorderPainted(false);
        btViewMore6.setContentAreaFilled(false);
        btViewMore6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore6.setFocusPainted(false);
        btViewMore6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore6ActionPerformed(evt);
            }
        });
        jPanel76.add(btViewMore6, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, -1));

        jPanel74.add(jPanel76);

        jPanel70.add(jPanel74);

        jPanel11.add(jPanel70);

        jPanel77.setBackground(new java.awt.Color(255, 255, 255));
        jPanel77.setLayout(new java.awt.GridLayout(1, 2, 15, 0));

        jPanel78.setLayout(new java.awt.GridLayout(1, 2));

        jPanel79.setBackground(new java.awt.Color(204, 204, 204));

        lbPicture7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình Pascal.png"))); // NOI18N
        jPanel79.add(lbPicture7);

        jPanel78.add(jPanel79);

        jPanel80.setBackground(new java.awt.Color(204, 204, 204));
        jPanel80.setForeground(new java.awt.Color(0, 0, 0));
        jPanel80.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle7.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle7.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle7.setText("Lập trình PasCal");
        jPanel80.add(lbTitle7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 250, 30));

        txtContent7.setColumns(20);
        txtContent7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtContent7.setForeground(new java.awt.Color(0, 0, 0));
        txtContent7.setRows(5);
        txtContent7.setText("asdasd");
        jScrollPane18.setViewportView(txtContent7);

        jPanel80.add(jScrollPane18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 250, 100));

        btViewMore7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore7.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore7.setText("View More");
        btViewMore7.setBorderPainted(false);
        btViewMore7.setContentAreaFilled(false);
        btViewMore7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore7.setFocusPainted(false);
        btViewMore7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore7ActionPerformed(evt);
            }
        });
        jPanel80.add(btViewMore7, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, -1));

        jPanel78.add(jPanel80);

        jPanel77.add(jPanel78);

        jPanel81.setBackground(new java.awt.Color(255, 255, 255));
        jPanel81.setLayout(new java.awt.GridLayout(1, 2));

        jPanel82.setBackground(new java.awt.Color(204, 204, 204));

        lbPicture8.setBackground(new java.awt.Color(255, 255, 255));
        lbPicture8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình SQL.png"))); // NOI18N
        jPanel82.add(lbPicture8);

        jPanel81.add(jPanel82);

        jPanel83.setBackground(new java.awt.Color(204, 204, 204));
        jPanel83.setForeground(new java.awt.Color(0, 0, 0));
        jPanel83.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle8.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle8.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle8.setText("Lập trình SQL");
        jPanel83.add(lbTitle8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 250, 30));

        txtContent8.setColumns(20);
        txtContent8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtContent8.setForeground(new java.awt.Color(0, 0, 0));
        txtContent8.setRows(5);
        txtContent8.setText("asdasd");
        jScrollPane19.setViewportView(txtContent8);

        jPanel83.add(jScrollPane19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 250, 100));

        btViewMore8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore8.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore8.setText("View More");
        btViewMore8.setBorderPainted(false);
        btViewMore8.setContentAreaFilled(false);
        btViewMore8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore8.setFocusPainted(false);
        btViewMore8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore8ActionPerformed(evt);
            }
        });
        jPanel83.add(btViewMore8, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, -1));

        jPanel81.add(jPanel83);

        jPanel77.add(jPanel81);

        jPanel11.add(jPanel77);

        jPanel84.setBackground(new java.awt.Color(255, 255, 255));
        jPanel84.setLayout(new java.awt.GridLayout(1, 2, 15, 0));

        jPanel85.setLayout(new java.awt.GridLayout(1, 2));

        jPanel86.setBackground(new java.awt.Color(204, 204, 204));

        lbPicture9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình python.png"))); // NOI18N
        jPanel86.add(lbPicture9);

        jPanel85.add(jPanel86);

        jPanel87.setBackground(new java.awt.Color(204, 204, 204));
        jPanel87.setForeground(new java.awt.Color(0, 0, 0));
        jPanel87.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle9.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle9.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle9.setText("Lập trình Python");
        jPanel87.add(lbTitle9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 250, 30));

        txtContent9.setColumns(20);
        txtContent9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtContent9.setForeground(new java.awt.Color(0, 0, 0));
        txtContent9.setRows(5);
        txtContent9.setText("asdasd");
        jScrollPane20.setViewportView(txtContent9);

        jPanel87.add(jScrollPane20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 250, 100));

        btViewMore9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore9.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore9.setText("View More");
        btViewMore9.setBorderPainted(false);
        btViewMore9.setContentAreaFilled(false);
        btViewMore9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore9.setFocusPainted(false);
        btViewMore9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore9ActionPerformed(evt);
            }
        });
        jPanel87.add(btViewMore9, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, -1));

        jPanel85.add(jPanel87);

        jPanel84.add(jPanel85);

        jPanel88.setBackground(new java.awt.Color(255, 255, 255));
        jPanel88.setLayout(new java.awt.GridLayout(1, 2));

        jPanel89.setBackground(new java.awt.Color(204, 204, 204));

        lbPicture10.setBackground(new java.awt.Color(255, 255, 255));
        lbPicture10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình C#.png"))); // NOI18N
        jPanel89.add(lbPicture10);

        jPanel88.add(jPanel89);

        jPanel90.setBackground(new java.awt.Color(204, 204, 204));
        jPanel90.setForeground(new java.awt.Color(0, 0, 0));
        jPanel90.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle10.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle10.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle10.setText("Lập trình C#");
        jPanel90.add(lbTitle10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 250, 30));

        txtContent10.setColumns(20);
        txtContent10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtContent10.setForeground(new java.awt.Color(0, 0, 0));
        txtContent10.setRows(5);
        txtContent10.setText("asdasd");
        jScrollPane21.setViewportView(txtContent10);

        jPanel90.add(jScrollPane21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 250, 100));

        btViewMore10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btViewMore10.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore10.setText("View More");
        btViewMore10.setBorderPainted(false);
        btViewMore10.setContentAreaFilled(false);
        btViewMore10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore10.setFocusPainted(false);
        btViewMore10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore10ActionPerformed(evt);
            }
        });
        jPanel90.add(btViewMore10, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 150, -1, -1));

        jPanel88.add(jPanel90);

        jPanel84.add(jPanel88);

        jPanel11.add(jPanel84);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("All Articles");

        txtTim.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTim.setForeground(new java.awt.Color(0, 0, 0));
        txtTim.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtTimFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTimFocusLost(evt);
            }
        });
        txtTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimActionPerformed(evt);
            }
        });

        btTim.setBackground(new java.awt.Color(255, 153, 102));
        btTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo/View.png"))); // NOI18N
        btTim.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 424, Short.MAX_VALUE)
                .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btTim, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(287, 287, 287))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(52, 52, 52)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 1116, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(76, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btTim, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)))
                .addContainerGap(1043, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(80, 80, 80)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 997, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(19, Short.MAX_VALUE)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.ipady = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jPanel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15ActionPerformed

    private void btViewMore1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore1ActionPerformed
        openBlogSingle(0);
    }//GEN-LAST:event_btViewMore1ActionPerformed

    private void btViewMore2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore2ActionPerformed
        openBlogSingle(1);
    }//GEN-LAST:event_btViewMore2ActionPerformed

    private void txtTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimActionPerformed
        searchCourses();
    }//GEN-LAST:event_txtTimActionPerformed

    private void btTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTimActionPerformed
        searchCourses();
    }//GEN-LAST:event_btTimActionPerformed

    private void btViewMore3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore3ActionPerformed
        openBlogSingle(2);
    }//GEN-LAST:event_btViewMore3ActionPerformed

    private void btViewMore4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore4ActionPerformed
        openBlogSingle(3);
    }//GEN-LAST:event_btViewMore4ActionPerformed

    private void btViewMore5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore5ActionPerformed
        openBlogSingle(4);
    }//GEN-LAST:event_btViewMore5ActionPerformed

    private void btViewMore6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore6ActionPerformed
        openBlogSingle(5);
    }//GEN-LAST:event_btViewMore6ActionPerformed

    private void btViewMore7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore7ActionPerformed
        openBlogSingle(6);
    }//GEN-LAST:event_btViewMore7ActionPerformed

    private void btViewMore8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore8ActionPerformed
        openBlogSingle(7);
    }//GEN-LAST:event_btViewMore8ActionPerformed

    private void btViewMore9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore9ActionPerformed
        openBlogSingle(8);
    }//GEN-LAST:event_btViewMore9ActionPerformed

    private void btViewMore10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore10ActionPerformed
        openBlogSingle(9);
    }//GEN-LAST:event_btViewMore10ActionPerformed

    private void txtTimFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimFocusGained
        if (txtTim.getText().equals("Nhập từ khóa tìm kiếm...")) {
            txtTim.setText("");
            txtTim.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_txtTimFocusGained

    private void txtTimFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTimFocusLost
        if (txtTim.getText().isEmpty()) {
            txtTim.setText("Nhập từ khóa tìm kiếm...");
            txtTim.setForeground(Color.GRAY);
        }
    }//GEN-LAST:event_txtTimFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btTim;
    private javax.swing.JButton btViewMore1;
    private javax.swing.JButton btViewMore10;
    private javax.swing.JButton btViewMore2;
    private javax.swing.JButton btViewMore3;
    private javax.swing.JButton btViewMore4;
    private javax.swing.JButton btViewMore5;
    private javax.swing.JButton btViewMore6;
    private javax.swing.JButton btViewMore7;
    private javax.swing.JButton btViewMore8;
    private javax.swing.JButton btViewMore9;
    private javax.swing.JButton jButton15;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel73;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel79;
    private javax.swing.JPanel jPanel80;
    private javax.swing.JPanel jPanel81;
    private javax.swing.JPanel jPanel82;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel88;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel90;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane20;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JLabel lbPicture1;
    private javax.swing.JLabel lbPicture10;
    private javax.swing.JLabel lbPicture2;
    private javax.swing.JLabel lbPicture3;
    private javax.swing.JLabel lbPicture4;
    private javax.swing.JLabel lbPicture5;
    private javax.swing.JLabel lbPicture6;
    private javax.swing.JLabel lbPicture7;
    private javax.swing.JLabel lbPicture8;
    private javax.swing.JLabel lbPicture9;
    private javax.swing.JLabel lbTitle1;
    private javax.swing.JLabel lbTitle10;
    private javax.swing.JLabel lbTitle2;
    private javax.swing.JLabel lbTitle3;
    private javax.swing.JLabel lbTitle4;
    private javax.swing.JLabel lbTitle5;
    private javax.swing.JLabel lbTitle6;
    private javax.swing.JLabel lbTitle7;
    private javax.swing.JLabel lbTitle8;
    private javax.swing.JLabel lbTitle9;
    private javax.swing.JTextArea txtContent1;
    private javax.swing.JTextArea txtContent10;
    private javax.swing.JTextArea txtContent2;
    private javax.swing.JTextArea txtContent3;
    private javax.swing.JTextArea txtContent4;
    private javax.swing.JTextArea txtContent5;
    private javax.swing.JTextArea txtContent6;
    private javax.swing.JTextArea txtContent7;
    private javax.swing.JTextArea txtContent8;
    private javax.swing.JTextArea txtContent9;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables
    // Method để refresh data từ database
    public void refreshFromDatabase() {
        courses = CourseData.getAllCourses();
        loadCoursesToUI();
        resetPanelColors();
    }
}
