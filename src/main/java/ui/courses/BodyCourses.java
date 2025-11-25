/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.courses;

import model.Course;
import ui.dashboard.HomePage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 *
 * @author DELL
 */
public class BodyCourses extends javax.swing.JPanel {

    private List<Course> courses;
    private JLabel[] titleLabels;
    private JLabel[] dateLabels;  // THÊM MỚI
    private JLabel[] priceLabels; // THÊM MỚI
    private JLabel[] pictureLabels;
    private JPanel[] coursePanels;
    private HomePage parentFrame; // THÊM MỚI

    /**
     * Creates new form bodyCourses
     */
    public BodyCourses() {
        initComponents();
        initCourseData();
        loadCoursesToUI();

        txtTim.setText("Nhập từ khóa tìm kiếm...");
        txtTim.setForeground(Color.GRAY);
        txtTim.setPreferredSize(txtTim.getPreferredSize());
    }

    public void setParentFrame(HomePage frame) {
        this.parentFrame = frame;
    }

    private void initCourseData() {
        courses = CourseData.getAllCourses();

        titleLabels = new JLabel[]{
                lbTitle1, lbTitle2, lbTitle3, lbTitle4, lbTitle5,
                lbTitle6, lbTitle7, lbTitle8, lbTitle9, lbTitle10
        };

        dateLabels = new JLabel[]{
                lbDate1, lbDate2, lbDate3, lbDate4, lbDate5,
                lbDate6, lbDate7, lbDate8, lbDate9, lbDate10
        };

        priceLabels = new JLabel[]{
                lbGia1, lbGia2, lbGia3, lbGia4, lbGia5,
                lbGia6, lbGia7, lbGia8, lbGia9, lbGia10,};

        pictureLabels = new JLabel[]{
                lbPicture1, lbPicture2, lbPicture3, lbPicture4, lbPicture5,
                lbPicture6, lbPicture7, lbPicture8, lbPicture9, lbPicture10

        };

        coursePanels = new JPanel[]{
                jPanel8, jPanel10, jPanel17, jPanel20, jPanel23,
                jPanel26, jPanel29, jPanel32, jPanel36, jPanel40
        };
    }

    private void loadCoursesToUI() {
        for (int i = 0; i < CourseData.getAllCourses().size(); i++) {
            Course course = courses.get(i);

            // Cập nhật tiêu đề
            titleLabels[i].setText(course.getTitle());
            titleLabels[i].putClientProperty("courseId", course.getCourseId());

            // Cập nhật ngày
            dateLabels[i].setText(CourseData.formatDate(course.getCreatedAt()));

            // Cập nhật giá
            priceLabels[i].setText(course.getPrice() == 0 ? "Free" : course.getPrice() + " VND");

            // Load hình ảnh
            loadCourseImage(course.getCourseId(), i);
        }
    }

    private void loadCourseImage(int courseId, int index) {
        if (index < 0 || index >= pictureLabels.length) {
            return;
        }

        String imagePath = CourseData.getImagePath(courseId);
        imagePath = imagePath.replace("/images610x360/", "/images/");
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            if (icon.getIconWidth() > 0) {
                pictureLabels[index].setIcon(icon);
            }
        } catch (Exception e) {
            System.err.println("Không thể load hình: " + imagePath);
        }
    }

    private void searchCourses() {
        String keyword = txtTim.getText().trim();
        resetPanelColors();

        if (keyword.isEmpty()) {
            return;
        }

        for (int i = 0; i < titleLabels.length; i++) {
            String title = titleLabels[i].getText().toLowerCase();
            if (title.contains(keyword.toLowerCase())) {
                highlightPanel(coursePanels[i]);
            }
        }
    }

    private void resetPanelColors() {
        Color defaultColor = new Color(255, 255, 255);
        for (JPanel panel : coursePanels) {
            panel.setBackground(defaultColor);
            panel.repaint();
        }
    }

    private void highlightPanel(JPanel panel) {
        Color highlightColor = new Color(255, 255, 0); // Màu vàng
        panel.setBackground(highlightColor);
        panel.repaint();
    }

    private void openCourseSingle(int index) {
        if (index < 0 || index >= courses.size()) {
            System.err.println("Index out of bounds: " + index + ", courses.size(): " + courses.size());
            return;
        }
        Course course = courses.get(index);
        System.out.println("Opening course: " + course.getTitle());  // Debug log
        CourseSingle courseSingle = new CourseSingle();
        courseSingle.loadCourseData(course);
        courseSingle.setParentFrame(parentFrame);
        navigateToCourseSingle(courseSingle);
    }

    private void navigateToCourseSingle(CourseSingle courseSingle) {
        if (parentFrame != null) {
            parentFrame.getBodyScrollPane().setViewportView(
                    parentFrame.wrapPanelCenter(courseSingle)
            );
            parentFrame.getBodyScrollPane().revalidate();
            parentFrame.getBodyScrollPane().repaint();
        } else {
            System.err.println("parentFrame is null! Cannot navigate.");
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

        jPanel38 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btTim = new javax.swing.JButton();
        txtTim = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        lbPicture1 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        lbTitle1 = new javax.swing.JLabel();
        lbGia1 = new javax.swing.JLabel();
        lbDate1 = new javax.swing.JLabel();
        btViewMore1 = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        lbPicture2 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        lbTitle2 = new javax.swing.JLabel();
        lbGia2 = new javax.swing.JLabel();
        lbDate2 = new javax.swing.JLabel();
        btViewMore2 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        lbPicture3 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        lbTitle3 = new javax.swing.JLabel();
        lbGia3 = new javax.swing.JLabel();
        lbDate3 = new javax.swing.JLabel();
        btViewMore3 = new javax.swing.JButton();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        lbPicture4 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        lbTitle4 = new javax.swing.JLabel();
        lbGia4 = new javax.swing.JLabel();
        lbDate4 = new javax.swing.JLabel();
        btViewMore4 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        lbPicture5 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        lbTitle5 = new javax.swing.JLabel();
        lbGia5 = new javax.swing.JLabel();
        lbDate5 = new javax.swing.JLabel();
        btViewMore5 = new javax.swing.JButton();
        jPanel26 = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        lbPicture6 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        lbTitle6 = new javax.swing.JLabel();
        lbGia6 = new javax.swing.JLabel();
        lbDate6 = new javax.swing.JLabel();
        btViewMore6 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel29 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        lbPicture7 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        lbTitle7 = new javax.swing.JLabel();
        lbGia7 = new javax.swing.JLabel();
        lbDate7 = new javax.swing.JLabel();
        btViewMore7 = new javax.swing.JButton();
        jPanel32 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        lbPicture8 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        lbTitle8 = new javax.swing.JLabel();
        lbGia8 = new javax.swing.JLabel();
        lbDate8 = new javax.swing.JLabel();
        btViewMore8 = new javax.swing.JButton();
        jPanel35 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        lbPicture9 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        lbTitle9 = new javax.swing.JLabel();
        lbGia9 = new javax.swing.JLabel();
        lbDate9 = new javax.swing.JLabel();
        btViewMore9 = new javax.swing.JButton();
        jPanel40 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        lbPicture10 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        lbTitle10 = new javax.swing.JLabel();
        lbGia10 = new javax.swing.JLabel();
        lbDate10 = new javax.swing.JLabel();
        btViewMore10 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridBagLayout());

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 32)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("All Courses");

        btTim.setBackground(new java.awt.Color(255, 153, 102));
        btTim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Logo/View.png"))); // NOI18N
        btTim.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btTim.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                btTimFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                btTimFocusLost(evt);
            }
        });
        btTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTimActionPerformed(evt);
            }
        });

        txtTim.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
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

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.GridLayout(5, 1, 30, 30));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new java.awt.GridLayout(1, 2, 90, 0));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình C++ cơ bản.png"))); // NOI18N
        jPanel11.add(lbPicture1);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle1.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle1.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle1.setText("Lập trình C++ cơ bản");
        jPanel12.add(lbTitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia1.setBackground(new java.awt.Color(255, 255, 255));
        lbGia1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbGia1.setForeground(new java.awt.Color(0, 0, 0));
        lbGia1.setText("Free");
        jPanel12.add(lbGia1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lbDate1.setBackground(new java.awt.Color(255, 255, 255));
        lbDate1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDate1.setForeground(new java.awt.Color(0, 0, 0));
        lbDate1.setText("Date");
        jPanel12.add(lbDate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        btViewMore1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btViewMore1.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore1.setText("View more");
        btViewMore1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btViewMore1.setBorderPainted(false);
        btViewMore1.setContentAreaFilled(false);
        btViewMore1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore1.setFocusPainted(false);
        btViewMore1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore1ActionPerformed(evt);
            }
        });
        jPanel12.add(btViewMore1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, 30));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel8);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình C++ cơ bản.png"))); // NOI18N
        jPanel15.add(lbPicture2);

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle2.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle2.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle2.setText("Lập trình C++ cơ bản");
        jPanel16.add(lbTitle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia2.setBackground(new java.awt.Color(255, 255, 255));
        lbGia2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbGia2.setForeground(new java.awt.Color(0, 0, 0));
        lbGia2.setText("Free");
        jPanel16.add(lbGia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lbDate2.setBackground(new java.awt.Color(255, 255, 255));
        lbDate2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDate2.setForeground(new java.awt.Color(0, 0, 0));
        lbDate2.setText("Date");
        jPanel16.add(lbDate2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        btViewMore2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btViewMore2.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore2.setText("View more");
        btViewMore2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btViewMore2.setBorderPainted(false);
        btViewMore2.setContentAreaFilled(false);
        btViewMore2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore2.setFocusPainted(false);
        btViewMore2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore2ActionPerformed(evt);
            }
        });
        jPanel16.add(btViewMore2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, 30));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel10);

        jPanel3.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new java.awt.GridLayout(1, 2, 90, 0));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình C++ cơ bản.png"))); // NOI18N
        jPanel18.add(lbPicture3);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle3.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle3.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle3.setText("Lập trình C++ cơ bản");
        jPanel19.add(lbTitle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia3.setBackground(new java.awt.Color(255, 255, 255));
        lbGia3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbGia3.setForeground(new java.awt.Color(0, 0, 0));
        lbGia3.setText("Free");
        jPanel19.add(lbGia3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lbDate3.setBackground(new java.awt.Color(255, 255, 255));
        lbDate3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDate3.setForeground(new java.awt.Color(0, 0, 0));
        lbDate3.setText("Date");
        jPanel19.add(lbDate3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        btViewMore3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btViewMore3.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore3.setText("View more");
        btViewMore3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btViewMore3.setBorderPainted(false);
        btViewMore3.setContentAreaFilled(false);
        btViewMore3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore3.setFocusPainted(false);
        btViewMore3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore3ActionPerformed(evt);
            }
        });
        jPanel19.add(btViewMore3, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, 30));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel17);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình C++ cơ bản.png"))); // NOI18N
        jPanel21.add(lbPicture4);

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle4.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle4.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle4.setText("Lập trình C++ cơ bản");
        jPanel22.add(lbTitle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia4.setBackground(new java.awt.Color(255, 255, 255));
        lbGia4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbGia4.setForeground(new java.awt.Color(0, 0, 0));
        lbGia4.setText("Free");
        jPanel22.add(lbGia4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lbDate4.setBackground(new java.awt.Color(255, 255, 255));
        lbDate4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDate4.setForeground(new java.awt.Color(0, 0, 0));
        lbDate4.setText("Date");
        jPanel22.add(lbDate4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        btViewMore4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btViewMore4.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore4.setText("View more");
        btViewMore4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btViewMore4.setBorderPainted(false);
        btViewMore4.setContentAreaFilled(false);
        btViewMore4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore4.setFocusPainted(false);
        btViewMore4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore4ActionPerformed(evt);
            }
        });
        jPanel22.add(btViewMore4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, 30));

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel20);

        jPanel3.add(jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new java.awt.GridLayout(1, 2, 90, 0));

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình C++ cơ bản.png"))); // NOI18N
        jPanel24.add(lbPicture5);

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle5.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle5.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle5.setText("Lập trình C++ cơ bản");
        jPanel25.add(lbTitle5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia5.setBackground(new java.awt.Color(255, 255, 255));
        lbGia5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbGia5.setForeground(new java.awt.Color(0, 0, 0));
        lbGia5.setText("Free");
        jPanel25.add(lbGia5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lbDate5.setBackground(new java.awt.Color(255, 255, 255));
        lbDate5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDate5.setForeground(new java.awt.Color(0, 0, 0));
        lbDate5.setText("Date");
        jPanel25.add(lbDate5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        btViewMore5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btViewMore5.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore5.setText("View more");
        btViewMore5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btViewMore5.setBorderPainted(false);
        btViewMore5.setContentAreaFilled(false);
        btViewMore5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore5.setFocusPainted(false);
        btViewMore5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore5ActionPerformed(evt);
            }
        });
        jPanel25.add(btViewMore5, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, 30));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel23);

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));
        jPanel26.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình C++ cơ bản.png"))); // NOI18N
        jPanel27.add(lbPicture6);

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle6.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle6.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle6.setText("Lập trình C++ cơ bản");
        jPanel28.add(lbTitle6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia6.setBackground(new java.awt.Color(255, 255, 255));
        lbGia6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbGia6.setForeground(new java.awt.Color(0, 0, 0));
        lbGia6.setText("Free");
        jPanel28.add(lbGia6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lbDate6.setBackground(new java.awt.Color(255, 255, 255));
        lbDate6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDate6.setForeground(new java.awt.Color(0, 0, 0));
        lbDate6.setText("Date");
        jPanel28.add(lbDate6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        btViewMore6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btViewMore6.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore6.setText("View more");
        btViewMore6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btViewMore6.setBorderPainted(false);
        btViewMore6.setContentAreaFilled(false);
        btViewMore6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore6.setFocusPainted(false);
        btViewMore6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore6ActionPerformed(evt);
            }
        });
        jPanel28.add(btViewMore6, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, 30));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel26);

        jPanel3.add(jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setLayout(new java.awt.GridLayout(1, 2, 90, 0));

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình C++ cơ bản.png"))); // NOI18N
        jPanel30.add(lbPicture7);

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle7.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle7.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle7.setText("Lập trình C++ cơ bản");
        jPanel31.add(lbTitle7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia7.setBackground(new java.awt.Color(255, 255, 255));
        lbGia7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbGia7.setForeground(new java.awt.Color(0, 0, 0));
        lbGia7.setText("Free");
        jPanel31.add(lbGia7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lbDate7.setBackground(new java.awt.Color(255, 255, 255));
        lbDate7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDate7.setForeground(new java.awt.Color(0, 0, 0));
        lbDate7.setText("Date");
        jPanel31.add(lbDate7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        btViewMore7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btViewMore7.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore7.setText("View more");
        btViewMore7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btViewMore7.setBorderPainted(false);
        btViewMore7.setContentAreaFilled(false);
        btViewMore7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore7.setFocusPainted(false);
        btViewMore7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore7ActionPerformed(evt);
            }
        });
        jPanel31.add(btViewMore7, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, 30));

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addGroup(jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addComponent(jPanel30, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel29);

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình C++ cơ bản.png"))); // NOI18N
        jPanel33.add(lbPicture8);

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle8.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle8.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle8.setText("Lập trình C++ cơ bản");
        jPanel34.add(lbTitle8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia8.setBackground(new java.awt.Color(255, 255, 255));
        lbGia8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbGia8.setForeground(new java.awt.Color(0, 0, 0));
        lbGia8.setText("Free");
        jPanel34.add(lbGia8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lbDate8.setBackground(new java.awt.Color(255, 255, 255));
        lbDate8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDate8.setForeground(new java.awt.Color(0, 0, 0));
        lbDate8.setText("Date");
        jPanel34.add(lbDate8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        btViewMore8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btViewMore8.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore8.setText("View more");
        btViewMore8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btViewMore8.setBorderPainted(false);
        btViewMore8.setContentAreaFilled(false);
        btViewMore8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore8.setFocusPainted(false);
        btViewMore8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore8ActionPerformed(evt);
            }
        });
        jPanel34.add(btViewMore8, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, 30));

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addGroup(jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel32Layout.createSequentialGroup()
                .addComponent(jPanel33, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel32);

        jPanel3.add(jPanel7);

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setLayout(new java.awt.GridLayout(1, 2, 90, 0));

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình C++ cơ bản.png"))); // NOI18N
        jPanel37.add(lbPicture9);

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));
        jPanel39.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle9.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle9.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle9.setText("Lập trình C++ cơ bản");
        jPanel39.add(lbTitle9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia9.setBackground(new java.awt.Color(255, 255, 255));
        lbGia9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbGia9.setForeground(new java.awt.Color(0, 0, 0));
        lbGia9.setText("Free");
        jPanel39.add(lbGia9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lbDate9.setBackground(new java.awt.Color(255, 255, 255));
        lbDate9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDate9.setForeground(new java.awt.Color(0, 0, 0));
        lbDate9.setText("Date");
        jPanel39.add(lbDate9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        btViewMore9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btViewMore9.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore9.setText("View more");
        btViewMore9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btViewMore9.setBorderPainted(false);
        btViewMore9.setContentAreaFilled(false);
        btViewMore9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore9.setFocusPainted(false);
        btViewMore9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore9ActionPerformed(evt);
            }
        });
        jPanel39.add(btViewMore9, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, 30));

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addGroup(jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel36Layout.createSequentialGroup()
                .addComponent(jPanel37, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel39, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel35.add(jPanel36);

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.lightGray, java.awt.Color.white, java.awt.Color.gray, java.awt.Color.lightGray));

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));

        lbPicture10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbPicture10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Lập trình C++ cơ bản.png"))); // NOI18N
        jPanel41.add(lbPicture10);

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));
        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbTitle10.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbTitle10.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle10.setText("Lập trình C++ cơ bản");
        jPanel42.add(lbTitle10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lbGia10.setBackground(new java.awt.Color(255, 255, 255));
        lbGia10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbGia10.setForeground(new java.awt.Color(0, 0, 0));
        lbGia10.setText("Free");
        jPanel42.add(lbGia10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lbDate10.setBackground(new java.awt.Color(255, 255, 255));
        lbDate10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbDate10.setForeground(new java.awt.Color(0, 0, 0));
        lbDate10.setText("Date");
        jPanel42.add(lbDate10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        btViewMore10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btViewMore10.setForeground(new java.awt.Color(0, 0, 0));
        btViewMore10.setText("View more");
        btViewMore10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        btViewMore10.setBorderPainted(false);
        btViewMore10.setContentAreaFilled(false);
        btViewMore10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btViewMore10.setFocusPainted(false);
        btViewMore10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btViewMore10ActionPerformed(evt);
            }
        });
        jPanel42.add(btViewMore10, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 50, -1, 30));

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addGroup(jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel42, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel40Layout.createSequentialGroup()
                .addComponent(jPanel41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel42, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel35.add(jPanel40);

        jPanel3.add(jPanel35);

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel38Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(392, 392, 392)
                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btTim, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(200, Short.MAX_VALUE))
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btTim, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1574, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 194;
        gridBagConstraints.ipady = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jPanel38, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void btViewMore1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore1ActionPerformed
        openCourseSingle(0);
    }//GEN-LAST:event_btViewMore1ActionPerformed

    private void btViewMore2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore2ActionPerformed
        openCourseSingle(1);
    }//GEN-LAST:event_btViewMore2ActionPerformed

    private void btViewMore3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore3ActionPerformed
        openCourseSingle(2);
    }//GEN-LAST:event_btViewMore3ActionPerformed

    private void btViewMore4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore4ActionPerformed
        openCourseSingle(3);
    }//GEN-LAST:event_btViewMore4ActionPerformed

    private void btViewMore5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore5ActionPerformed
        openCourseSingle(4);
    }//GEN-LAST:event_btViewMore5ActionPerformed

    private void btViewMore6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore6ActionPerformed
        openCourseSingle(5);
    }//GEN-LAST:event_btViewMore6ActionPerformed

    private void btViewMore7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore7ActionPerformed
        openCourseSingle(6);
    }//GEN-LAST:event_btViewMore7ActionPerformed

    private void btViewMore8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore8ActionPerformed
        openCourseSingle(7);
    }//GEN-LAST:event_btViewMore8ActionPerformed

    private void btViewMore9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore9ActionPerformed
        openCourseSingle(8);
    }//GEN-LAST:event_btViewMore9ActionPerformed

    private void btViewMore10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btViewMore10ActionPerformed
        openCourseSingle(9);
    }//GEN-LAST:event_btViewMore10ActionPerformed

    private void txtTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimActionPerformed
        searchCourses();
    }//GEN-LAST:event_txtTimActionPerformed

    private void btTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTimActionPerformed
        searchCourses();
    }//GEN-LAST:event_btTimActionPerformed

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

    private void btTimFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btTimFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_btTimFocusGained

    private void btTimFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btTimFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_btTimFocusLost


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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lbDate1;
    private javax.swing.JLabel lbDate10;
    private javax.swing.JLabel lbDate2;
    private javax.swing.JLabel lbDate3;
    private javax.swing.JLabel lbDate4;
    private javax.swing.JLabel lbDate5;
    private javax.swing.JLabel lbDate6;
    private javax.swing.JLabel lbDate7;
    private javax.swing.JLabel lbDate8;
    private javax.swing.JLabel lbDate9;
    private javax.swing.JLabel lbGia1;
    private javax.swing.JLabel lbGia10;
    private javax.swing.JLabel lbGia2;
    private javax.swing.JLabel lbGia3;
    private javax.swing.JLabel lbGia4;
    private javax.swing.JLabel lbGia5;
    private javax.swing.JLabel lbGia6;
    private javax.swing.JLabel lbGia7;
    private javax.swing.JLabel lbGia8;
    private javax.swing.JLabel lbGia9;
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
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables
}
