package ui.submission;

import model.*;
import service.Judge0Service;
import ui.components.CustomDialog;
import ui.dashboard.HomePage;
import ui.exercises.BodyCodingExercises;
import ui.exercises.ExerciseDetail;
import ui.exercises.TestDataExample;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * SubmissionResult - Panel hiển thị kết quả chấm bài
 */
public class SubmissionResult extends javax.swing.JPanel {

    private Course currentCourse;
    private CodingQuestion currentCodingQuestion;
    private Submission currentSubmission;
    private HomePage parentFrame;

    public SubmissionResult() {
        initComponents();
        setupTable();
    }

    private void setupTable() {
        // Tạo table model với các cột cần thiết
        String[] columns = {"Test Case", "Input", "Expected Output", "Actual Output", "Status", "Time", "Memory"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblTestCases.setModel(model);

        // Căn giữa nội dung các cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        tblTestCases.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblTestCases.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tblTestCases.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tblTestCases.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        // Custom renderer cho cột Status (màu sắc)
        tblTestCases.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus,
                                                           int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);

                String status = value.toString();
                if (status.equals("Accepted")) {
                    c.setForeground(new Color(0, 153, 0)); // Xanh lá
                } else if (status.contains("Error")) {
                    c.setForeground(Color.RED);
                } else {
                    c.setForeground(new Color(255, 140, 0)); // Cam
                }

                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                } else {
                    c.setBackground(Color.WHITE);
                }
                return c;
            }
        });

        // Điều chỉnh độ rộng cột
        tblTestCases.getColumnModel().getColumn(0).setPreferredWidth(100);  // Test Case
        tblTestCases.getColumnModel().getColumn(1).setPreferredWidth(150);  // Input
        tblTestCases.getColumnModel().getColumn(2).setPreferredWidth(150);  // Expected
        tblTestCases.getColumnModel().getColumn(3).setPreferredWidth(150);  // Actual
        tblTestCases.getColumnModel().getColumn(4).setPreferredWidth(120);  // Status
        tblTestCases.getColumnModel().getColumn(5).setPreferredWidth(80);   // Time
        tblTestCases.getColumnModel().getColumn(6).setPreferredWidth(80);   // Memory

        tblTestCases.setRowHeight(30);
    }

    public void setParentFrame(HomePage frame) {
        this.parentFrame = frame;
    }

    public void setCurrentCodingQuestion(CodingQuestion currentCodingQuestion) {
        this.currentCodingQuestion = currentCodingQuestion;
        lbTitle.setText(currentCodingQuestion.getTitle());
    }

    public void setSubmissionData(Submission submission) {
        this.currentSubmission = submission;
    }

    public void setCurrentCourse(Course currentCourse) {
        this.currentCourse = currentCourse;
    }

    public void startJudging() {
        if (currentSubmission == null) return;

        // Hiển thị trạng thái đang chấm
        lblStatus.setText("Đang chấm bài...");
        progressBar.setIndeterminate(true);
        progressBar.setVisible(true);

        // Vô hiệu hóa các button
        btDetail.setEnabled(false);
        btExercisesList.setEnabled(false);
        jButton1.setEnabled(false);

        // Xóa dữ liệu cũ
        DefaultTableModel model = (DefaultTableModel) tblTestCases.getModel();
        model.setRowCount(0);
        jTextArea1.setText("");

        new Thread(() -> {
            try {
                // Gọi service chấm bài với callback real-time
                SubmissionResultData result = Judge0Service.judgeSubmission(
                        currentSubmission,
                        (tcResult, currentIndex, total) -> {
                            SwingUtilities.invokeLater(() -> {
                                updateTestCaseRow(tcResult);
                                updateProgress(currentIndex, total);
                                appendTestCaseToTextArea(tcResult);
                            });
                        }
                );

                // Cập nhật UI sau khi có kết quả cuối cùng
                SwingUtilities.invokeLater(() -> showFinalResult(result));

            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    progressBar.setVisible(false);
                    jTextArea1.append("\n\n LỖI: Data sẽ không được lưu\n");
                    CustomDialog.getInstance().showError(
                            "Ây da lỗi tí: " + e.getMessage(),
                            "Lỗi rồi!!!"
                    );
                    btDetail.setEnabled(true);
                    btExercisesList.setEnabled(true);
                    jButton1.setEnabled(true);
                });
            }
        }).start();
    }

    private void updateTestCaseRow(TestCaseResultData tc) {
        DefaultTableModel model = (DefaultTableModel) tblTestCases.getModel();
        model.addRow(new Object[]{
                tc.getName(),
                truncateText(tc.getInputData(), 50),
                truncateText(tc.getExpectedOutput(), 50),
                truncateText(tc.getActualOutput(), 50),
                tc.getStatus(),
                tc.getTimeDisplay(),
                tc.getMemoryDisplay()
        });

        int lastRow = tblTestCases.getRowCount() - 1;
        if (lastRow >= 0) {
            tblTestCases.scrollRectToVisible(tblTestCases.getCellRect(lastRow, 0, true));
        }
    }

    private void appendTestCaseToTextArea(TestCaseResultData tc) {
        String line = String.format("▶ %s: %s [%s, %s]\n",
                tc.getName(),
                tc.getStatus(),
                tc.getTimeDisplay(),
                tc.getMemoryDisplay()
        );
        jTextArea1.append(line);
        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());
    }

    private void updateProgress(int current, int total) {
        progressBar.setIndeterminate(false);
        progressBar.setMaximum(total);
        progressBar.setValue(current);
        lblStatus.setText(String.format("Đang chấm... (%d/%d test cases)", current, total));
    }

    private void showFinalResult(SubmissionResultData result) {
        // Ẩn progress bar
        progressBar.setVisible(false);

        // CẬP NHẬT CÁC LABEL NGAY LẬP TỨC
        lbTaiNguyen.setText(String.format("%.3fs, %.2f MB", result.getTotalTime(), result.getAvgMemory()));
        lbTime.setText(String.format("%.3fs", result.getMaxTime()));
        lbDiem.setText(String.format("%.0f/%.0f (%.0f/%d điểm)",
                result.getScore(), result.getMaxScore(),
                result.getScore(), (int) result.getMaxScore()));

        // Cập nhật status
        lblStatus.setText(String.format("Kết quả: %s (%d/%d test cases passed)",
                result.getOverallStatus(),
                result.getPassedCount(),
                result.getTotalCount()));


        jTextArea1.setCaretPosition(jTextArea1.getDocument().getLength());

        // Kích hoạt lại các button
        btDetail.setEnabled(true);
        btExercisesList.setEnabled(true);
        jButton1.setEnabled(true);

        // Hiển thị dialog kết quả
        if (result.getOverallStatus().equals("Accepted")) {
            CustomDialog.getInstance().showSuccess(
                    String.format("Chúc mừng! Bạn đã hoàn thành bài tập với điểm %.0f/%.0f",
                            result.getScore(), result.getMaxScore()),
                    "Chấm bài thành công"
            );
        } else {
            CustomDialog.getInstance().showWarning(
                    String.format("Bài làm chưa đúng. Điểm: %.0f/%.0f (%d/%d test cases passed)",
                            result.getScore(), result.getMaxScore(),
                            result.getPassedCount(), result.getTotalCount()),
                    "Kết quả chấm bài"
            );
        }
    }

    private String truncateText(String text, int maxLength) {
        if (text == null) return "";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength) + "...";
    }

    private void goBackToSubmissionPage() {
        if (currentCodingQuestion != null && parentFrame != null) {
            SubmissionPage submissionPage = new SubmissionPage();
            submissionPage.setExerciseData(currentCodingQuestion);
            submissionPage.setParentFrame(parentFrame);

            submissionPage.setCurrentCourse(currentCourse);
            submissionPage.setCurrentCodingQuestion(currentCodingQuestion);

            parentFrame.getBodyScrollPane().setViewportView(parentFrame.wrapPanelCenter(submissionPage));
            parentFrame.getBodyScrollPane().revalidate();
            parentFrame.getBodyScrollPane().repaint();
        }
    }

    private void goBackToExerciseDetail() {
        if (currentCodingQuestion != null && parentFrame != null) {
            ExerciseDetail detailPanel = new ExerciseDetail();
            detailPanel.setExerciseData(currentCodingQuestion);
            detailPanel.setCurrentCourse(currentCourse);
            detailPanel.setParentFrame(parentFrame);
            parentFrame.getBodyScrollPane().setViewportView(parentFrame.wrapPanelCenter(detailPanel));
            parentFrame.getBodyScrollPane().revalidate();
            parentFrame.getBodyScrollPane().repaint();
        }
    }

    private void goBackToCodingExercises() {
        if (parentFrame != null) {
            BodyCodingExercises codingPanel = new BodyCodingExercises();
            codingPanel.setParentFrame(parentFrame);
            if (currentCourse != null) {
                TestDataExample.addSampleData(codingPanel);
                codingPanel.callBackForLoadTable(currentCourse);
            } else {
                TestDataExample.addSampleData(codingPanel);
            }
            parentFrame.getBodyScrollPane().setViewportView(parentFrame.wrapPanelCenter(codingPanel));
            parentFrame.getBodyScrollPane().revalidate();
            parentFrame.getBodyScrollPane().repaint();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        java.awt.GridBagConstraints gridBagConstraints;

        jPanel4 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbTaiNguyen = new javax.swing.JLabel();
        lbTime = new javax.swing.JLabel();
        lbDiem = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btDetail = new javax.swing.JButton();
        btExercisesList = new javax.swing.JButton();
        lbTitle = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        progressBar = new JProgressBar();
        lblStatus = new JLabel();
        tblTestCases = new JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.GridBagLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Tài nguyên:");

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Thời gian chạy test lâu nhất:");

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Điểm cuối cùng:");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));

        lbTaiNguyen.setBackground(new java.awt.Color(255, 255, 255));
        lbTaiNguyen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbTaiNguyen.setForeground(new java.awt.Color(0, 0, 0));
        lbTaiNguyen.setText("0,000s 0,00MB");

        lbTime.setBackground(new java.awt.Color(255, 255, 255));
        lbTime.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbTime.setForeground(new java.awt.Color(0, 0, 0));
        lbTime.setText("0,000s");

        lbDiem.setBackground(new java.awt.Color(255, 255, 255));
        lbDiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lbDiem.setForeground(new java.awt.Color(0, 0, 0));
        lbDiem.setText("0/0 (0/0 Điểm)");

        jButton1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setOpaque(true);
        jButton1.setBackground(Color.WHITE);
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Nộp lại");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton1ActionPerformed(evt);
        });

        btDetail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btDetail.setContentAreaFilled(false);
        btDetail.setOpaque(true);
        btDetail.setBackground(Color.WHITE);
        btDetail.setForeground(new java.awt.Color(0, 0, 0));
        btDetail.setText("Detail");
        btDetail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btDetail.addActionListener(this::btDetailActionPerformed);

        btExercisesList.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btExercisesList.setContentAreaFilled(false);
        btExercisesList.setOpaque(true);
        btExercisesList.setBackground(Color.WHITE);
        btExercisesList.setForeground(new java.awt.Color(0, 0, 0));
        btExercisesList.setText("Exercises list");
        btExercisesList.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btExercisesList.addActionListener(this::btExercisesListActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lbTaiNguyen, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lbTime, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(lbDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(btExercisesList)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(btDetail)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jButton1))
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1018, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(lbTaiNguyen, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel5)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel3)
                                                .addComponent(lbTime, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jLabel4)
                                                        .addComponent(lbDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(jButton1)
                                                        .addComponent(btDetail)
                                                        .addComponent(btExercisesList))
                                                .addContainerGap(19, Short.MAX_VALUE))))
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
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 1041, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 30, Short.MAX_VALUE))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(lbTitle)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.ipady = 33;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jPanel4, gridBagConstraints);
    }
    // </editor-fold>//GEN-END:initComponents


    private void btDetailActionPerformed(java.awt.event.ActionEvent evt) {
        goBackToExerciseDetail();
    }

    private void btExercisesListActionPerformed(java.awt.event.ActionEvent evt) {
        goBackToCodingExercises();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        goBackToSubmissionPage();
    }

    // Variables declaration
    private javax.swing.JButton btDetail;
    private javax.swing.JButton btExercisesList;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lbDiem;
    private javax.swing.JLabel lbTaiNguyen;
    private javax.swing.JLabel lbTime;
    private javax.swing.JLabel lbTitle;

    // Components mới cần thêm
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JTable tblTestCases;
    private javax.swing.JScrollPane scrollPaneTestCases;
}
