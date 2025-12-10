package ui;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;

public class TextSectionPanel extends JPanel {

    private JTextArea textArea;

    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        textArea.setText(text);
    }

    public void clear() {
        textArea.setText("");
        removeHighlights();
    }

    private void removeHighlights() {
        textArea.getHighlighter().removeAllHighlights();
    }

    public TextSectionPanel(String title, boolean isEditable) {
        setLayout(new BorderLayout());
        setBackground(Theme.BG_COLOR);
        textArea = Theme.createDarkTextArea();
        textArea.setEditable(isEditable);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
                null, title, 0, 0, Theme.FONT_NORMAL, Color.GRAY));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void highlightText(String word) {
        removeHighlights();
        if (word == null || word.isEmpty()) return;

        Highlighter highlighter = textArea.getHighlighter();
        Highlighter.HighlightPainter painter =
                new DefaultHighlighter.DefaultHighlightPainter(Theme.HIGHLIGHT_COLOR);

        String content = textArea.getText();
        String lowerContent = content.toLowerCase();
        String lowerWord = word.toLowerCase();

        int index = 0;
        while ((index = lowerContent.indexOf(lowerWord, index)) >= 0) {
            try {
                int end = index + lowerWord.length();
                highlighter.addHighlight(index, end, painter);
                index = end;
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }
    }

}
