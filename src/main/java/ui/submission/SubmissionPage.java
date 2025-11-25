/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package ui.submission;

import app.AppConfig;
import app.SessionManager;
import exception.DatabaseException;
import model.CodingQuestion;
import model.Course;
import model.Submission;
import model.User;
import ui.components.CustomDialog;
import ui.dashboard.HomePage;
import ui.exercises.ExerciseDetail;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 *
 * @author DELL
 */
public class SubmissionPage extends javax.swing.JPanel {

    private CodingQuestion currentCodingQuestion;
    private Course currentCourse;
    private HomePage parentFrame;
    private final DefaultTableModel tableModel;

    /**
     * Creates new form SubmissionPage
     */
    public SubmissionPage() {
        initComponents();
        tableModel = (DefaultTableModel) tblLichSu.getModel();
        setUpTable();
        txtNopBai.setText("VUI LÒNG CHỌN ĐÚNG NGÔN NGỮ!\n" +
                "LƯU Ý C/C++:\n" +
                "• PHẢI có int main() { return 0; }\n" +
                "• Sử dụng scanf()/cin để đọc input\n" +
                "• KHÔNG include file tự tạo");
    }

    public void setParentFrame(HomePage frame) {
        this.parentFrame = frame;
    }

    private void setUpTable() {
        // === Renderer căn giữa và tô màu theo trạng thái ===
        DefaultTableCellRenderer customRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Lấy status từ cột 3 (index 3)
                String status = table.getValueAt(row, 3).toString();

                // Căn giữa tất cả các cột
                setHorizontalAlignment(SwingConstants.CENTER);

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(Color.WHITE);
                } else {
                    if (status.equalsIgnoreCase("Accepted")) {
                        c.setForeground(new Color(0, 120, 0)); // Chữ màu xanh đậm
                        c.setBackground(new Color(220, 255, 220)); // Nền xanh nhạt
                    } else {
                        c.setForeground(new Color(180, 0, 0)); // Chữ màu đỏ đậm
                        c.setBackground(new Color(255, 220, 220));
                    }
                }
                return c;
            }
        };

        // Áp dụng renderer cho tất cả các cột
        tblLichSu.setDefaultRenderer(Object.class, customRenderer);
        tblLichSu.setDefaultRenderer(Number.class, customRenderer);
        tblLichSu.setDefaultRenderer(Integer.class, customRenderer);

        // Trang trí header
        tblLichSu.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblLichSu.getTableHeader().setBackground(new Color(245, 245, 245));
        tblLichSu.getTableHeader().setForeground(Color.BLACK);
    }

    public void setCurrentCourse(Course currentCourse) {
        this.currentCourse = currentCourse;
        loadSubmissionHistory();
    }

    public void setCurrentCodingQuestion(CodingQuestion currentCodingQuestion) {
        this.currentCodingQuestion = currentCodingQuestion;
    }

    public void setExerciseData(CodingQuestion exercise) {
        this.currentCodingQuestion = exercise;
        lbTitle.setText(currentCodingQuestion.getTitle());
    }

    private void submitCode() {
        String code = txtNopBai.getText().trim();

        // Kiểm tra nếu không có code
        if (code.isEmpty() || code.trim().length() < 10) {
            CustomDialog.getInstance().showWarning("Vui lòng nhập code trước khi nộp bài!", "Thông báo");
            return;
        }

        // Lấy thông tin
        User currentUser = SessionManager.getInstance().getCurrentUser();
        String language = cbxNgonNgu.getSelectedItem().toString();

        Submission submission = new Submission();
        submission.setCode(code);
        submission.setLanguage(language);
        submission.setQuestionId(currentCodingQuestion.getQuestionId());
        submission.setEnrollmentId(AppConfig.getEnrollmentService().getEnrollmentById(currentUser.getUserId(), currentCourse.getCourseId()).getEnrollmentId());
        submission.setScore(currentCodingQuestion.getDifficulty().equals("Dễ") ? 10.0 : currentCodingQuestion.getDifficulty().equals("Trung bình") ? 20.0 : 30.0);

        goSubmissionResult(submission);

    }

    private void goSubmissionResult(Submission submission) {
        SubmissionResult submissionResult = new SubmissionResult();
        submissionResult.setParentFrame(parentFrame);
        parentFrame.getBodyScrollPane().setViewportView(parentFrame.wrapPanelCenter(submissionResult));

        submissionResult.setSubmissionData(submission);
        submissionResult.setCurrentCodingQuestion(currentCodingQuestion);
        submissionResult.setCurrentCourse(currentCourse);

        parentFrame.getBodyScrollPane().revalidate();
        parentFrame.getBodyScrollPane().repaint();

        // Gọi sau khi Swing đã render xong frame hiện tại
        SwingUtilities.invokeLater(() -> {
            submissionResult.startJudging(); // bắt đầu chấm sau khi panel đã được hiển thị
        });
    }

    private void goBackToExerciseDetail() {
        if (currentCodingQuestion != null && parentFrame != null) {
            ExerciseDetail detailPanel = new ExerciseDetail();
            detailPanel.setExerciseData(currentCodingQuestion);
            detailPanel.setParentFrame(parentFrame);

            detailPanel.setCurrentCourse(currentCourse);

            parentFrame.getBodyScrollPane().setViewportView(parentFrame.wrapPanelCenter(detailPanel));
            parentFrame.getBodyScrollPane().revalidate();
            parentFrame.getBodyScrollPane().repaint();
        }
    }

    private void loadSubmissionHistory() {
        // Xóa dữ liệu cũ trong bảng
        tableModel.setRowCount(0);

        try {
            User currentUser = SessionManager.getInstance().getCurrentUser();
            List<Submission> submissions = AppConfig.getAnalyticsService().getQuestionSubmissionHistory(currentUser.getUserId(), currentCourse.getCourseId(), currentCodingQuestion.getQuestionId());

            for (Submission submission : submissions) {
                Object[] rowData = new Object[]{
                        submission.getSubmissionId(),
                        submission.getLanguage(),
                        submission.getSubmitTime(),
                        submission.getStatus(),
                        (int) submission.getScore()
                };
                tableModel.addRow(rowData);
            }
        } catch (DatabaseException e) {
            CustomDialog.getInstance().showError("Lỗi khi tải lịch sử nộp bài: " + e.getMessage(), "Lỗi dữ liệu");
        }
    }

    private String getLanguageWarning(String language) {
        switch (language.toLowerCase()) {
            case "java":
                return "VUI LÒNG CHỌN ĐÚNG NGÔN NGỮ!\n" +
                        " LƯU Ý JAVA:\n" +
                        "• Class chính PHẢI tên là 'Main'\n" +
                        "• Phải có: public static void main(String[] args)\n" +
                        "• Sử dụng Scanner để đọc input từ System.in";

            case "python":
                return "VUI LÒNG CHỌN ĐÚNG NGÔN NGỮ!\n" +
                        "LƯU Ý PYTHON:\n" +
                        "• KHÔNG cần định nghĩa function main()\n" +
                        "• Sử dụng input() để đọc dữ liệu\n" +
                        "• Code chạy trực tiếp từ dòng đầu tiên";

            case "c":
                return "VUI LÒNG CHỌN ĐÚNG NGÔN NGỮ!\n" +
                        "LƯU Ý C/C++:\n" +
                        "• PHẢI có int main() { return 0; }\n" +
                        "• Sử dụng scanf()/cin để đọc input\n" +
                        "• KHÔNG include file tự tạo";
            case "c++":
                return "VUI LÒNG CHỌN ĐÚNG NGÔN NGỮ!\n" +
                        "LƯU Ý C/C++:\n" +
                        "• PHẢI có int main() { return 0; }\n" +
                        "• Sử dụng scanf()/cin để đọc input\n" +
                        "• KHÔNG include file tự tạo";

            case "pascal":
                return "VUI LÒNG CHỌN ĐÚNG NGÔN NGỮ!\n" +
                        "LƯU Ý PASCAL:\n" +
                        "• Sử dụng readln() và writeln()\n" +
                        "• Kết thúc chương trình bằng dấu chấm (.)\n" +
                        "• Phải khai báo biến trong phần var";

            case "c#":
                return "VUI LÒNG CHỌN ĐÚNG NGÔN NGỮ!\n" +
                        "LƯU Ý C#:\n" +
                        "• Main phải là static method\n" +
                        "• Sử dụng Console.ReadLine() và Console.WriteLine()\n" +
                        "• Cần using System;";

            case "sql":
                return "VUI LÒNG CHỌN ĐÚNG NGÔN NGỮ!\n" +
                        "LƯU Ý SQL:\n" +
                        "• Sử dụng SQLite syntax\n" +
                        "• Database/table đã có sẵn\n" +
                        "• Chỉ viết query SELECT/INSERT/UPDATE/DELETE";

            default:
                return "";
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

        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtNopBai = new javax.swing.JTextArea();
        cbxNgonNgu = new javax.swing.JComboBox<>();
        btThoat = new javax.swing.JButton();
        btNopBai = new javax.swing.JButton();
        btXoaCode = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        scrollpane = new javax.swing.JScrollPane();
        tblLichSu = new javax.swing.JTable();
        lbTitle = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridBagLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        txtNopBai.setBackground(new java.awt.Color(255, 255, 255));
        txtNopBai.setColumns(20);
        txtNopBai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNopBai.setForeground(new java.awt.Color(0, 0, 0));
        txtNopBai.setRows(5);
        jScrollPane1.setViewportView(txtNopBai);

        cbxNgonNgu.setBackground(new java.awt.Color(255, 255, 255));
        cbxNgonNgu.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        cbxNgonNgu.setForeground(new java.awt.Color(0, 0, 0));
        cbxNgonNgu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"C", "C++", "Java", "Python", "Pascal", "SQL", "C#", " "}));
        cbxNgonNgu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cbxNgonNgu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxNgonNguActionPerformed(evt);
            }
        });

        btThoat.setBackground(new java.awt.Color(255, 255, 255));
        btThoat.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btThoat.setForeground(new java.awt.Color(0, 0, 0));
        btThoat.setText("Thoát");
        btThoat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btThoatActionPerformed(evt);
            }
        });

        btNopBai.setBackground(new java.awt.Color(102, 255, 102));
        btNopBai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btNopBai.setForeground(new java.awt.Color(0, 0, 0));
        btNopBai.setText("Nộp bài");
        btNopBai.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btNopBai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNopBaiActionPerformed(evt);
            }
        });

        btXoaCode.setBackground(new java.awt.Color(255, 255, 255));
        btXoaCode.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btXoaCode.setForeground(new java.awt.Color(0, 0, 0));
        btXoaCode.setText("Xóa Code");
        btXoaCode.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btXoaCode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btXoaCodeActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Code editor");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Ngôn ngữ : ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btThoat)
                                .addGap(33, 33, 33))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cbxNgonNgu, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btXoaCode)
                                                .addGap(18, 18, 18)
                                                .addComponent(btNopBai)
                                                .addGap(16, 16, 16))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1014, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(cbxNgonNgu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btXoaCode)
                                        .addComponent(btNopBai))
                                .addGap(12, 12, 12)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btThoat)
                                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Lịch sử nộp bài");

        tblLichSu.setBackground(new java.awt.Color(255, 255, 255));
        tblLichSu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tblLichSu.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null},
                        {null, null, null, null, null}
                },
                new String[]{
                        "ID", "Ngôn Ngữ", "Thời Gian", "Trạng Thái", "Điểm"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                    false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        scrollpane.setViewportView(tblLichSu);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel4)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 1033, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollpane, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1051, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1033, Short.MAX_VALUE))
                                        .addContainerGap(9, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1214, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(6, 6, 6)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        lbTitle.setBackground(new java.awt.Color(255, 255, 255));
        lbTitle.setFont(new java.awt.Font("Segoe UI", 0, 28)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(0, 0, 0));
        lbTitle.setText("asas");

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Nộp Bài");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1107, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                        .addComponent(jLabel1)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 996, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                        .addGap(30, 30, 30)
                                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(0, 14, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1317, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                        .addGap(5, 5, 5)
                                                        .addComponent(lbTitle)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 44, Short.MAX_VALUE)))
        );

        add(jPanel4, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents

    private void btXoaCodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btXoaCodeActionPerformed
        txtNopBai.setText("");
    }//GEN-LAST:event_btXoaCodeActionPerformed

    private void btNopBaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNopBaiActionPerformed
        submitCode();
    }//GEN-LAST:event_btNopBaiActionPerformed

    private void btThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btThoatActionPerformed
        goBackToExerciseDetail();
    }//GEN-LAST:event_btThoatActionPerformed

    private void cbxNgonNguActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        String ngonngu = cbxNgonNgu.getSelectedItem().toString();
        String languageWarning = getLanguageWarning(ngonngu);
        txtNopBai.setText(languageWarning);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btNopBai;
    private javax.swing.JButton btThoat;
    private javax.swing.JButton btXoaCode;
    private javax.swing.JComboBox<String> cbxNgonNgu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JScrollPane scrollpane;
    private javax.swing.JTable tblLichSu;
    private javax.swing.JTextArea txtNopBai;
    // End of variables declaration//GEN-END:variables
}
