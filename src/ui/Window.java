package ui;

import core.map.HMap;
import service.TextAnalysisService;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{

    private TextSectionPanel inputPanel;
    private TextSectionPanel outputPanel;
    private ControlPanel controlPanel;
    private TextAnalysisService textAnalysisService;

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
        inputPanel.setText("Paste text to analyze here...\nJava is cool.");
        centerGrid.add(inputPanel);
        centerGrid.add(outputPanel);
        mainContainer.add(centerGrid, BorderLayout.CENTER);
        controlPanel = new ControlPanel();
        textAnalysisService = new TextAnalysisService();
        setupListeners();
        mainContainer.add(controlPanel, BorderLayout.SOUTH);
        add(mainContainer);
    }

    private void setupListeners() {
        controlPanel.addAnalyzeListener(e -> {
            String op = controlPanel.getSelectedOperation();
            String content = inputPanel.getText();
            String searchWord = controlPanel.getSearchWord();
            outputPanel.setText(content);
            if ("Word Frequency".equals(op)) {
                Integer count = countOccurrences(content, searchWord);
                controlPanel.updateResultLabel("Count: " + count);
                outputPanel.highlightText(searchWord);
            }
        });

        // Clear
        controlPanel.addClearListener(e -> {
            inputPanel.clear();
            outputPanel.clear();
            controlPanel.clearInputs();
        });
    }

    private Integer countOccurrences(String content, String word) {
        return textAnalysisService.wordFrequency(content,word);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Window().setVisible(true));
    }

}
