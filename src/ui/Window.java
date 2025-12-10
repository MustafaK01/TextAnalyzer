package ui;

import service.TextAnalysisService;
import service.TextOperationsService;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Window extends JFrame{

    private TextSectionPanel inputPanel;
    private TextSectionPanel outputPanel;
    private ControlPanel controlPanel;
    private TextAnalysisService textAnalysisService;
    private TextOperationsService textOperationsService;


    public Window() {
        setTitle("Text Analyzer");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
    }

    private void initUI() {
        JPanel mainContainer = new JPanel(new BorderLayout(10, 10));
        mainContainer.setBackground(Theme.BG_COLOR);
        mainContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel titleLabel = new JLabel("Text Analyzer", SwingConstants.CENTER);
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.TEXT_COLOR);
        mainContainer.add(titleLabel, BorderLayout.NORTH);
        JPanel centerGrid = new JPanel(new GridLayout(1, 2, 20, 0));
        centerGrid.setBackground(Theme.BG_COLOR);
        inputPanel = new TextSectionPanel("Input", true);
        outputPanel = new TextSectionPanel("Output", false);
        inputPanel.setText("Paste text to analyze here...\n" +
                "Java is cool.Paste text to analyze here...\n" +
                "Java is cool.Paste text to analyze here...\n" +
                "Java is cool.Paste text to analyze here...\n" +
                "Java is cool.Paste text to analyze here...\n" +
                "Java is cool.Paste text to analyze here...\n" +
                "Java is cool.Paste text to analyze here...\n" +
                "Java is cool.Paste text to analyze here...\n" +
                "Java is cool.");
        centerGrid.add(inputPanel);
        centerGrid.add(outputPanel);
        mainContainer.add(centerGrid, BorderLayout.CENTER);
        controlPanel = new ControlPanel();
        textAnalysisService = new TextAnalysisService();
        textOperationsService = new TextOperationsService();
        setupListeners();
        mainContainer.add(controlPanel, BorderLayout.SOUTH);
        add(mainContainer);
    }

    private void setupListeners() {
        controlPanel.addSearchInputListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                countOccurrences();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                countOccurrences();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                countOccurrences();
            }
        });

        inputPanel.getTextArea().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }
            @Override
            public void removeUpdate(DocumentEvent e) {

            }
            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });

        inputPanel.getTextArea().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    textOperationsService.saveState(inputPanel.getText());
                }
                else if (e.getKeyCode() == KeyEvent.VK_Z && e.isControlDown()) {
                    String oldText = textOperationsService.undo(inputPanel.getText());
                    if (oldText != null) {
                        inputPanel.setText(oldText);
                    }
                    e.consume();
                }
                else if (e.getKeyCode() == KeyEvent.VK_Y && e.isControlDown()) {
                    String newText = textOperationsService.redo(inputPanel.getText());
                    if (newText != null) {
                        inputPanel.setText(newText);
                    }
                    e.consume();
                }
            }
        });
        controlPanel.addClearListener(e -> {
            inputPanel.clear();
            outputPanel.clear();
            controlPanel.clearInputs();
        });
    }

    private void countOccurrences() {
        String op = controlPanel.getSelectedOperation();
        String content = inputPanel.getText();
        String searchInput = controlPanel.getSearchWord();
        if (content.isEmpty()) return;
        outputPanel.setText(content);
        if ("Word Frequency".equals(op)) {
            Integer count = textAnalysisService.wordFrequency(content,searchInput);
            controlPanel.updateResultLabel("Count: " + count);
            outputPanel.highlightText(searchInput);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Window().setVisible(true));
    }

}
