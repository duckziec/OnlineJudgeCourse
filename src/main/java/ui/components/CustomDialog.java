package ui.components;

import javax.swing.*;
import java.awt.*;

public class CustomDialog extends JDialog {
    protected static JPanel contentPanel = null;
    private static CustomDialog customDialog;

    public CustomDialog(Frame parent, String title, boolean modal) {
        super(parent, title, modal);
        initializeDialog();
    }

    private CustomDialog() {
        initializeDialog();
    }

    public static CustomDialog getInstance() {
        if (customDialog == null) {
            customDialog = new CustomDialog();
        }
        return customDialog;
    }

    private void initializeDialog() {
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(contentPanel);
    }

    public void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    public void showError(String message, String title) {
        showMessage(message, title, JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message, String title) {
        showMessage(message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void showWarning(String message, String title) {
        showMessage(message, title, JOptionPane.WARNING_MESSAGE);
    }
}
