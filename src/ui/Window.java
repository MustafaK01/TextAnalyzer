package ui;

import core.map.HMap;
import core.profiling.EntropyAnalyzer;
import core.profiling.EntropyVisualizer;
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
    private EntropyAnalyzer entropyAnalyzer;
    private EntropyVisualizer entropyVisualizer;

    public Window() {
        setTitle("Text Analyzer");
        setSize(1920, 1079);
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
        entropyAnalyzer = new EntropyAnalyzer();
        entropyVisualizer = new EntropyVisualizer();
        setupListeners();
        mainContainer.add(controlPanel, BorderLayout.SOUTH);
        add(mainContainer);
    }

    private void setupListeners() {
        controlPanel.addSearchInputListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                routeAction();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                countOccurrences();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                routeAction();
            }
        });

        controlPanel.addOperationListener(e -> {
            routeAction();
        });

        controlPanel.addContextWindowSizeListener(e -> {
            if ("Context Analysis".equals(controlPanel.getSelectedOperation())) {
                routeAction();
            }
        });

        controlPanel.addEntropyWindowSizeListener(e -> {
            if ("Entropy (Sliding Profile)".equals(controlPanel.getSelectedOperation())) {
                routeAction();
            }
        });

        inputPanel.getTextArea().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                routeAction();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                routeAction();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                routeAction();
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

    private void routeAction() {
        String op = controlPanel.getSelectedOperation();

        if ("Word Frequency".equals(op)) countOccurrences();
        else if ("Context Analysis".equals(op)) getContextWindowSize();
        else if ("Entropy (Global)".equals(op)) showGlobalEntropy();
        else if ("Entropy (Sliding Profile)".equals(op)) getEntropyWindowSize();

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

    private void getContextWindowSize(){
        String content = inputPanel.getText();
        String searchInput = controlPanel.getSearchWord();
        int windowSize = controlPanel.getContextWindowSize();

        if (content.isEmpty() || searchInput.isEmpty()){
            outputPanel.clear();
            return;
        }
        HMap<String, Integer> result = textAnalysisService.frequencyWindow(content,searchInput,windowSize);
        if (result != null) {
            outputPanel.setText(result.toString());
        } else {
            outputPanel.setText("Sonuç bulunamadı.");
        }
    }

    private void showGlobalEntropy() {
        String content = inputPanel.getText();
        if (content == null || content.isEmpty()) return;

        double h = entropyAnalyzer.globalEntropy(content);

        controlPanel.updateResultLabel(String.format("H: %.3f bits/char", h));
        outputPanel.setText(
                "Global Character Shannon Entropy\n" +
                        "-------------------------------\n" +
                        String.format("H = %.6f bits/char\n", h)
        );
    }

    private void getEntropyWindowSize() {
        String content = inputPanel.getText();
        if (content == null || content.isEmpty()) return;

        int windowSize = controlPanel.getEntropyWindowSize();
        int step = Math.max(1, windowSize / 10);
        var profile = entropyAnalyzer.slidingEntropy(content, windowSize, step);

        double global = entropyAnalyzer.globalEntropy(content);
        double threshold = Math.min(7.5, global + 1.0);

        String bars = entropyVisualizer.asciiBars(profile, 50, threshold, windowSize, step);

        core.profiling.EntropyHeatmap heatmap = new core.profiling.EntropyHeatmap();
        var segs = heatmap.buildSegments(profile, windowSize, step, threshold);
        String heat = heatmap.formatHeatmap(segs, windowSize, step, threshold);
        controlPanel.updateResultLabel(String.format("Global: %.3f  Thr: %.3f", global, threshold));
        outputPanel.setText(bars + "\n\n" + heat);
        System.out.println("entropy windowSize from UI = " + controlPanel.getEntropyWindowSize());
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Window().setVisible(true));
    }

}
