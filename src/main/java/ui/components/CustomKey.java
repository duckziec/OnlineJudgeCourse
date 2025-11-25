package ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * CustomKey - Singleton tiện ích để gán phím Enter cho các JButton (login/register...).
 */
public final class CustomKey {

    private static CustomKey instance;

    private CustomKey() {
        // private constructor để đảm bảo Singleton
    }

    public static CustomKey getInstance() {
        if (instance == null) {
            synchronized (CustomKey.class) {
                if (instance == null) {
                    instance = new CustomKey();
                }
            }
        }
        return instance;
    }

    /**
     * Gán phím Enter cho 1 nút cụ thể.
     * Khi người dùng nhấn Enter trong vùng focus của nút, nút sẽ tự click.
     */
    public void bindEnterKeyToButton(JButton button) {
        if (button == null) return;

        button.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    button.doClick();
                }
            }
        });
    }

    /**
     * Gán phím Enter cho toàn bộ Container (form, panel, frame...).
     * Khi người dùng nhấn Enter ở bất kỳ JTextField hoặc JPasswordField nào,
     * nút được truyền vào sẽ tự click.
     */
    public void bindEnterKeyToContainer(Container container, JButton button) {
        if (container == null || button == null) return;

        for (Component c : container.getComponents()) {
            if (c instanceof JTextField || c instanceof JPasswordField) {
                c.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                            button.doClick();
                        }
                    }
                });
            } else if (c instanceof Container) {
                // gọi đệ quy cho panel con
                bindEnterKeyToContainer((Container) c, button);
            }
        }
    }
}
