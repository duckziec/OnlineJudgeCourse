/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.exercises;

import app.AppConfig;
import app.SessionManager;
import exception.DatabaseException;
import model.CodingQuestion;
import model.Course;
import model.Lesson;
import ui.auth.Login;
import ui.components.CustomDialog;
import ui.components.CustomKey;
import ui.courses.CourseData;
import ui.dashboard.HomePage;
import utils.StringUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class BodyCodingExercises extends javax.swing.JPanel {

    /**
     * Creates new form BodyCodingExercises
     */
    private final List<Exercises> exerciseList = new ArrayList<>();
    private HomePage parentFrame;
    private Course currentCourse = null;
    private List<Lesson> currentLessonList;
    private List<String> currentCategoryList;

    public BodyCodingExercises() {
        initComponents();

        // 1. Cài đặt Renderer tùy chỉnh để JComboBox hiển thị tên khóa học
        cbCourses.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Course) {
                    setText(((Course) value).getTitle());
                } else if (value != null) {
                    setText(value.toString());
                }
                return this;
            }
        });

        loadCourseList();
        loadTable();

        // đổ dữ liệu vào JTable
        resizeColumnWidth(jTable1);  // căn chỉnh độ rộng cột

// Cho phép xuống dòng trong cột "Bài"
        TableColumn baiColumn = jTable1.getColumnModel().getColumn(2); // Cột Bài là cột 2
        baiColumn.setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

                JTextArea area = new JTextArea();
                area.setText(value == null ? "" : value.toString());
                area.setLineWrap(true);
                area.setWrapStyleWord(true);
                area.setOpaque(true);
                area.setFont(table.getFont());
                area.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

                // Lấy status để tô màu
                String status = table.getValueAt(row, 0).toString();

                if (isSelected) {
                    area.setBackground(table.getSelectionBackground());
                    area.setForeground(Color.WHITE);
                } else {
                    if (status.equalsIgnoreCase("Hoàn thành")) {
                        area.setForeground(new Color(0, 120, 0));
                        area.setBackground(new Color(220, 255, 220));
                    } else {
                        area.setForeground(Color.DARK_GRAY);
                        area.setBackground(Color.WHITE);
                    }
                }
                return area;
            }
        });

        // Cho phép chiều cao hàng tự động điều chỉnh theo nội dung
        setDefaultLessonData();

        // gán nút enter cho nút tìm
        CustomKey.getInstance().bindEnterKeyToContainer(this, btTim);

        // chỉnh table
        resizeColumnWidth(jTable1);

    }

    public void setParentFrame(HomePage frame) {
        this.parentFrame = frame;
    }

    private void openExerciseDetail(int lessonId) {
        if (currentCourse == null) {
            Exercises exercise = findExerciseById(lessonId);
            if (exercise != null && parentFrame != null) {
                ExerciseDetail detailPanel = new ExerciseDetail();
                detailPanel.setExerciseData(exercise);
                detailPanel.setParentFrame(parentFrame);
                parentFrame.getBodyScrollPane().setViewportView(parentFrame.wrapPanelCenter(detailPanel));
                parentFrame.getBodyScrollPane().revalidate();
                parentFrame.getBodyScrollPane().repaint();
            }
        } else {
            CodingQuestion codingQuestion = AppConfig.getLessonService().getCodingQuestionById(lessonId);
            if (codingQuestion != null && parentFrame != null) {
                ExerciseDetail detailPanel = new ExerciseDetail();
                detailPanel.setExerciseData(codingQuestion);
                detailPanel.setParentFrame(parentFrame);

                detailPanel.setCurrentCourse(currentCourse);

                parentFrame.getBodyScrollPane().setViewportView(parentFrame.wrapPanelCenter(detailPanel));
                parentFrame.getBodyScrollPane().revalidate();
                parentFrame.getBodyScrollPane().repaint();
            }
        }

    }

    private Exercises findExerciseById(int id) {
        for (Exercises ex : exerciseList) {
            if (ex.getId() == id) {
                return ex;
            }
        }
        return null;
    }

    private void loadTable2(List<Lesson> lessons) {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Xóa dữ liệu cũ trên bảng

        if (currentCourse == null) {
            loadTable();
            return;
        }
        if (lessons == null) {
            return;
        }

        for (Lesson lesson : lessons) {
            model.addRow(new Object[]{
                    lesson.getStatus(),
                    lesson.getLessonId(),
                    lesson.getTitle(),
                    lesson.getCategory(),
                    AppConfig.getLessonService().getScoreByDifficulty(lesson.getDifficulty()),
                    String.format("%.1f%%", lesson.getAcptPercent()),
                    lesson.getAcpt()
            });
        }
    }

    private void loadTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (Exercises e : exerciseList) {
            model.addRow(new Object[]{
                    "Chưa hoàn thành",
                    e.getId(),
                    e.getBai(),
                    e.getNhom(),
                    e.getDiem(),
                    e.getAcPercent(),
                    e.getAcCount()
            });
        }
    }

    public void addExercise(Exercises exercise) {
        exerciseList.add(exercise);
        loadTable();
    }

    private void loadCourseList() {
        cbCourses.removeAllItems();
        cbCourses.addItem("Tất cả bài tập"); // Thêm tùy chọn xem tất cả

        List<Course> courses = CourseData.getUserCourses();
        if (courses != null && !courses.isEmpty()) {
            for (Course course : courses) {
                cbCourses.addItem(course); // Thêm toàn bộ đối tượng Course
            }
        }
    }

    private void resizeColumnWidth(JTable table) {
        jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        int tableWidth = 900; // fallback nếu table chưa được hiển thị
        // Tỉ lệ chiều rộng từng cột
        double[] columnRatios = {0.18, 0.06, 0.33, 0.20, 0.08, 0.08, 0.07};
        for (int i = 0; i < table.getColumnCount(); i++) {
            int colWidth = (int) (tableWidth * columnRatios[i]);
            table.getColumnModel().getColumn(i).setPreferredWidth(colWidth);
        }
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.revalidate();

        // === Renderer chung cho tô màu cả hàng Status = "Hoàn thành" ===
        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String status = table.getValueAt(row, 0).toString(); // cột Status = 0

                // Căn trái cho cột Bài (cột 2), còn lại căn giữa
                if (column == 2) {
                    setHorizontalAlignment(SwingConstants.LEFT);
                } else {
                    setHorizontalAlignment(SwingConstants.CENTER);
                }

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(Color.WHITE);
                } else {
                    if (status.equalsIgnoreCase("Hoàn thành")) {
                        c.setForeground(new Color(0, 120, 0));
                        c.setBackground(new Color(220, 255, 220));
                    } else {
                        c.setForeground(Color.DARK_GRAY);
                        c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        };

        // Áp dụng renderer cho tất cả các cột
        table.setDefaultRenderer(Object.class, customRenderer);
        // Áp dụng renderer cho cột kiểu Number (nếu cột ID là kiểu Integer)
        table.setDefaultRenderer(Number.class, customRenderer);
        table.setDefaultRenderer(Integer.class, customRenderer);

        // Trang trí bảng
        table.setRowHeight(28);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(245, 245, 245));
        table.getTableHeader().setForeground(Color.BLACK);
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

        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtCanTim = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        cbxNhom = new javax.swing.JComboBox<>();
        cbxDiem = new javax.swing.JComboBox<>();
        btTim = new javax.swing.JButton();
        btThoat = new javax.swing.JButton();
        cbCourses = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridBagLayout());

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 28)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Exercise List");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jLabel1, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        txtCanTim.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCanTim.setForeground(new java.awt.Color(0, 0, 0));
        txtCanTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCanTimActionPerformed(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Khoảng Điểm");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Nhập Bài Cần Tìm");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Nhóm");

        cbxNhom.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxNhom.setForeground(new java.awt.Color(0, 0, 0));
        cbxNhom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"All", "Item 2", "Item 3", "Item 4"}));
        cbxNhom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxNhomActionPerformed(evt);
            }
        });

        cbxDiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbxDiem.setForeground(new java.awt.Color(0, 0, 0));
        cbxDiem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"1", "Item 2", "Item 3", "Item 4"}));
        cbxDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxDiemActionPerformed(evt);
            }
        });

        btTim.setBackground(new java.awt.Color(255, 153, 102));
        btTim.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btTim.setForeground(new java.awt.Color(255, 255, 255));
        btTim.setText("Tìm");
        btTim.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btTim.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTimActionPerformed(evt);
            }
        });

        btThoat.setBackground(new java.awt.Color(255, 153, 102));
        btThoat.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btThoat.setForeground(new java.awt.Color(255, 255, 255));
        btThoat.setText("Thoát");
        btThoat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        btThoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThoatActionPerformed(evt);
            }
        });

        cbCourses.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cbCourses.setToolTipText("");
        cbCourses.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Courses"));
        cbCourses.setPreferredSize(new java.awt.Dimension(65, 47));
        cbCourses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCoursesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGap(6, 6, 6)
                                                        .addComponent(jLabel4))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGap(6, 6, 6)
                                                        .addComponent(jLabel5))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGap(6, 6, 6)
                                                        .addComponent(jLabel3))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGap(6, 6, 6)
                                                        .addComponent(cbxDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGap(81, 81, 81)
                                                        .addComponent(btThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(btTim, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(cbxNhom, javax.swing.GroupLayout.Alignment.LEADING, 0, 259, Short.MAX_VALUE)
                                                        .addComponent(txtCanTim, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(cbCourses, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                .addGap(0, 44, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(cbCourses, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtCanTim, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addGap(18, 18, 18)
                                .addComponent(cbxNhom, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(cbxDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btTim, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTable1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null},
                        {null, null, null, null, null, null, null}
                },
                new String[]{
                        "Status", "ID", "Bài", "Nhóm", "Điểm", "%AC", "#AC"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable1PropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
            jTable1.getColumnModel().getColumn(5).setResizable(false);
            jTable1.getColumnModel().getColumn(6).setResizable(false);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 902, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 44;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        add(jPanel4, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCanTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCanTimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCanTimActionPerformed

    private void cbxNhomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxNhomActionPerformed
        // TODO add your handling code here:
        findingLesson();
    }//GEN-LAST:event_cbxNhomActionPerformed

    private void cbxDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxDiemActionPerformed
        // TODO add your handling code here:
        findingLesson();
    }//GEN-LAST:event_cbxDiemActionPerformed

    private void btTimActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        findingLesson();
    }

    private void btThoatActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        txtCanTim.setText("");                // Xóa ô tìm kiếm
        cbxNhom.setSelectedIndex(0);          // Reset nhóm về "All"// Reset dạng bài
        cbxDiem.setSelectedIndex(0);          // Reset khoảng điểm
        loadTable2(currentLessonList);                          // Hiện lại toàn bộ danh sách ban đầu
    }

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            // Kiểm tra đăng nhập trước khi mở bài tập
            if (!SessionManager.getInstance().isLoggedIn()) {
                CustomDialog.getInstance().showWarning("Vui lòng đăng nhập để tiếp tục!", "Thông báo");

                // Chuyển đến trang Login
                if (parentFrame != null) {
                    Login loginPanel = new Login();
                    loginPanel.setParentFrame(parentFrame);
                    parentFrame.getBodyScrollPane().setViewportView(parentFrame.wrapPanelCenter(loginPanel));
                    parentFrame.getBodyScrollPane().revalidate();
                    parentFrame.getBodyScrollPane().repaint();
                }
                return;
            }

            // Đã đăng nhập - cho phép mở bài tập
            int row = jTable1.getSelectedRow();
            if (row != -1) {
                int id = (int) jTable1.getValueAt(row, 1);
                openExerciseDetail(id);
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1PropertyChange

    private void cbCoursesActionPerformed(java.awt.event.ActionEvent evt) {
        Object selectedItem = cbCourses.getSelectedItem();
        if (selectedItem instanceof Course selectedCourse) {
            // Nếu người dùng chọn một khóa học
            setUpDataLessons(selectedCourse);
            loadTable2(currentLessonList);
        } else {
            // Nếu người dùng chọn "Tất cả bài tập"
            setDefaultLessonData();
            loadTable();
        }
    }

    private void findingLesson() {
        // Get search criteria from the UI
        String keyword = StringUtil.removeVietnameseDiacritics(txtCanTim.getText().trim().toLowerCase());
        String nhom = cbxNhom.getSelectedItem().toString();
        String diemStr = cbxDiem.getSelectedItem().toString();

        // Get the current list of lessons from the service;
        if (currentLessonList == null) {
            return; // No lessons to filter
        }

        // Create a new list to hold the filtered results
        List<Lesson> filteredList = new ArrayList<>();

        // Nếu không có điều kiện lọc nào, hiển thị tất cả
        if (keyword.isEmpty() && nhom.equals("Tất cả") && diemStr.equals("Tất cả")) {
            filteredList = currentLessonList;
        } else {
            for (Lesson lesson : currentLessonList) {
                String titleNormalized = StringUtil.removeVietnameseDiacritics(lesson.getTitle().toLowerCase());
                boolean keywordMatch = keyword.isEmpty() || titleNormalized.contains(keyword);
                boolean nhomMatch = nhom.equals("Tất cả") || (lesson.getCategory() != null && lesson.getCategory().contains(nhom));

                boolean diemMatch = diemStr.equals("Tất cả");
                if (!diemMatch) {
                    try {
                        int selectedDiem = Integer.parseInt(diemStr);
                        int lessonScore = app.AppConfig.getLessonService().getScoreByDifficulty(lesson.getDifficulty());
                        diemMatch = (lessonScore == selectedDiem);
                    } catch (NumberFormatException ignored) {

                    }
                }

                if (keywordMatch && nhomMatch && diemMatch) {
                    filteredList.add(lesson);
                }
            }
        }
        // Cập nhật bảng với danh sách đã lọc
        loadTable2(filteredList);
    }

    public void callBackForLoadTable(Course course) {
        if (course == null) {
            loadTable();
        }
        cbCourses.setSelectedItem(course);
        loadTable2(currentLessonList);
    }

    private void setUpDataLessons(Course course) {
        this.currentCourse = course;
        AppConfig.getLessonService().reloadLessonsWithStatus(SessionManager.getInstance().getCurrentUser().getUserId(), course.getCourseId());
        currentLessonList = AppConfig.getLessonService().getAllLessons();

        for (Lesson lesson : currentLessonList) {
            lesson.setTitle(String.format("Bài %d: %s", lesson.getOrderIndex(), lesson.getTitle()));
            lesson.setStatus(lesson.getStatus().equalsIgnoreCase("Done") ? "Hoàn thành" : "Chưa hoàn thành");
        }
        currentCategoryList = AppConfig.getLessonService().getCategoryNames();
        currentCategoryList.addFirst("Tất cả");
        cbxNhom.setModel(new DefaultComboBoxModel<>(currentCategoryList.toArray(new String[0])));

        try {
            double progress = AppConfig.getAnalyticsService().getProgressCourse(SessionManager.getInstance().getCurrentUser().getUserId(), course.getCourseId());
            cbCourses.setToolTipText(String.format("Tiến độ khóa học: %.1f%%", progress));
        } catch (DatabaseException e) {
            CustomDialog.getInstance().showError("Lỗi lấy tiến độ khóa học!", "Lỗi dữ liệu");
        }
    }

    private void setDefaultLessonData() {
        currentCourse = null;
        currentLessonList = null;
        currentCategoryList = null;
        jTable1.setRowHeight(28);
        cbxNhom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
                "Tất cả", "Lý Thuyết Số", "Mảng 1 Chiều", "String"
        }));
        cbxDiem.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
                "Tất cả", "10", "20", "30"
        }));
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btThoat;
    private javax.swing.JButton btTim;
    private javax.swing.JComboBox<Object> cbCourses;
    private javax.swing.JComboBox<String> cbxDiem;
    private javax.swing.JComboBox<String> cbxNhom;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtCanTim;
    // End of variables declaration//GEN-END:variables

}
