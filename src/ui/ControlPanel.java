package ui;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class ControlPanel extends JPanel {
    private JComboBox<String> operationSelector;
    private JTextField searchField;
    private JLabel frequencyLabel;

    private JPanel dynamicInputPanel;
    private JButton clearBtn;

    private JSpinner contextWindowSpinner;
    private JSpinner entropyWindowSpinner;
    private JLabel contextWindowLabel;
    private JLabel entropyWindowLabel;
    private SpinnerNumberModel contextWindowModel;
    private SpinnerNumberModel entropyWindowModel;

    private JLabel searchLabel;

    public ControlPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        setBackground(Theme.BG_COLOR);

        initComponents();
        setupLayout();
        setupInternalLogic();
    }

    private void initComponents() {
        String[] options = {"Select Operation...", "Word Frequency", "Context Analysis","Entropy (Global)","Entropy (Sliding Profile)"};
        operationSelector = new JComboBox<>(options);
        operationSelector.setFont(Theme.FONT_NORMAL);

        searchField = new JTextField(10);
        searchField.setFont(Theme.FONT_NORMAL);

        frequencyLabel = new JLabel("");
        frequencyLabel.setForeground(Theme.HIGHLIGHT_COLOR);
        frequencyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        contextWindowModel = new SpinnerNumberModel(2, 1, 10, 1);
        entropyWindowModel = new SpinnerNumberModel(200, 20, 5000, 10);
        contextWindowSpinner = new JSpinner(contextWindowModel);
        entropyWindowSpinner = new JSpinner(entropyWindowModel);
        contextWindowSpinner.setFont(Theme.FONT_NORMAL);
        entropyWindowSpinner.setFont(Theme.FONT_NORMAL);
        contextWindowLabel = new JLabel("Context Window:");
        contextWindowLabel.setForeground(Color.LIGHT_GRAY);
        entropyWindowLabel = new JLabel("Entropy Window:");
        entropyWindowLabel.setForeground(Color.LIGHT_GRAY);
        ((JSpinner.DefaultEditor) contextWindowSpinner.getEditor()).getTextField().setColumns(2);
        ((JSpinner.DefaultEditor) entropyWindowSpinner.getEditor()).getTextField().setColumns(4);
        searchLabel = new JLabel("Target Word:");
        searchLabel.setForeground(Color.LIGHT_GRAY);

        clearBtn = Theme.createButton("CLEAR", Color.GRAY);
    }

    private void setupLayout() {
        dynamicInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        dynamicInputPanel.setBackground(Theme.BG_COLOR);
        dynamicInputPanel.setVisible(false);

        searchLabel.setVisible(false);
        searchField.setVisible(false);
        contextWindowLabel.setVisible(false);
        contextWindowSpinner.setVisible(false);
        entropyWindowLabel.setVisible(false);
        entropyWindowSpinner.setVisible(false);
        frequencyLabel.setVisible(false);

        dynamicInputPanel.add(searchLabel);
        dynamicInputPanel.add(searchField);
        dynamicInputPanel.add(contextWindowLabel);
        dynamicInputPanel.add(contextWindowSpinner);
        dynamicInputPanel.add(entropyWindowLabel);
        dynamicInputPanel.add(entropyWindowSpinner);
        dynamicInputPanel.add(frequencyLabel);

        add(operationSelector);
        add(dynamicInputPanel);
        add(clearBtn);
    }

    private void setupInternalLogic() {
        operationSelector.addActionListener(e -> {
            String selected = (String) operationSelector.getSelectedItem();
            boolean isFreq = "Word Frequency".equals(selected);
            boolean isContext = "Context Analysis".equals(selected);
            boolean isEntropyGlobal = "Entropy (Global)".equals(selected);
            boolean isEntropySliding = "Entropy (Sliding Profile)".equals(selected);
            boolean showPanel = isFreq || isContext || isEntropyGlobal || isEntropySliding;
            dynamicInputPanel.setVisible(showPanel);

            if(showPanel) {
                searchLabel.setVisible(isFreq || isContext);
                searchField.setVisible(isFreq || isContext);
                contextWindowLabel.setVisible(isContext);
                contextWindowSpinner.setVisible(isContext);
                entropyWindowLabel.setVisible(isEntropySliding);
                entropyWindowSpinner.setVisible(isEntropySliding);
                frequencyLabel.setVisible(isFreq || isEntropyGlobal);

                if (isFreq || isContext) searchField.setText("");
                frequencyLabel.setText("");
            }
            revalidate();
            repaint();
        });
    }

    public String getSelectedOperation() {
        return (String) operationSelector.getSelectedItem();
    }

    public String getSearchWord() {
        return searchField.getText();
    }

    public int getContextWindowSize() {
        return (Integer) contextWindowSpinner.getValue();
    }

    public int getEntropyWindowSize() {
        return (Integer) entropyWindowSpinner.getValue();
    }

    public void updateResultLabel(String text) {
        frequencyLabel.setText(text);
    }

    public void clearInputs() {
        searchField.setText("");
        frequencyLabel.setText("");
        operationSelector.setSelectedIndex(0);
        // contextWindowSpinner.setValue(2);
        // entropyWindowSpinner.setValue(200);
    }

    public void addClearListener(ActionListener listener) {
        clearBtn.addActionListener(listener);
    }

    public void addOperationListener(ActionListener listener) {
        operationSelector.addActionListener(listener);
    }

    public void addContextWindowSizeListener(ChangeListener listener) {
        contextWindowSpinner.addChangeListener(listener);
    }

    public void addEntropyWindowSizeListener(ChangeListener listener) {
        entropyWindowSpinner.addChangeListener(listener);
    }

    public void addSearchInputListener(DocumentListener listener) {
        searchField.getDocument().addDocumentListener(listener);
    }
}
