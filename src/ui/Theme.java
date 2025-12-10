package ui;

import javax.swing.*;
import java.awt.*;

public class Theme {

    public static final Color BG_COLOR = Color.BLACK;
    public static final Color TEXT_AREA_BG = new Color(30, 30, 30);
    public static final Color TEXT_COLOR = Color.WHITE;
    public static final Color ACCENT_COLOR = new Color(70, 130, 180); // Steel Blue
    public static final Color HIGHLIGHT_COLOR = Color.YELLOW;
    public static final Font FONT_TITLE = new Font("Arial", Font.BOLD, 24);
    public static final Font FONT_NORMAL = new Font("Arial", Font.PLAIN, 14);
    public static final Font FONT_MONO = new Font("Monospaced", Font.PLAIN, 14);

    // Button
    public static JButton createButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        return btn;
    }

    // TextArea
    public static JTextArea createDarkTextArea() {
        JTextArea area = new JTextArea();
        area.setBackground(TEXT_AREA_BG);
        area.setForeground(TEXT_COLOR);
        area.setCaretColor(TEXT_COLOR);
        area.setFont(FONT_MONO);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        return area;
    }
}
