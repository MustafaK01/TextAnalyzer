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
    private JSpinner windowSizeSpinner;
    private JLabel windowLabel;

    private JPanel dynamicInputPanel;
    private JButton clearBtn;

    public ControlPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));
        setBackground(Theme.BG_COLOR);

        initComponents();
        setupLayout();
        setupInternalLogic();
    }

    private void initComponents() {
        String[] options = {"Select Operation...", "Word Frequency", "Context Analysis"};
        operationSelector = new JComboBox<>(options);
        operationSelector.setFont(Theme.FONT_NORMAL);

        searchField = new JTextField(10);
        searchField.setFont(Theme.FONT_NORMAL);

        frequencyLabel = new JLabel("");
        frequencyLabel.setForeground(Theme.HIGHLIGHT_COLOR);
        frequencyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        windowSizeSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 10, 1));
        windowSizeSpinner.setFont(Theme.FONT_NORMAL);
        JComponent editor = windowSizeSpinner.getEditor();
        JFormattedTextField tf = ((JSpinner.DefaultEditor) editor).getTextField();
        tf.setColumns(2);

        windowLabel = new JLabel("Window:");
        windowLabel.setForeground(Color.LIGHT_GRAY);

        clearBtn = Theme.createButton("CLEAR", Color.GRAY);
    }

    private void setupLayout() {
        dynamicInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        dynamicInputPanel.setBackground(Theme.BG_COLOR);
        dynamicInputPanel.setVisible(false);

        JLabel searchLabel = new JLabel("Target Word:");
        searchLabel.setForeground(Color.LIGHT_GRAY);

        dynamicInputPanel.add(searchLabel);
        dynamicInputPanel.add(searchField);
        dynamicInputPanel.add(windowLabel);
        dynamicInputPanel.add(windowSizeSpinner);
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
            boolean showSearchPanel = isFreq || isContext;
            dynamicInputPanel.setVisible(showSearchPanel);

            if(showSearchPanel) {
                windowLabel.setVisible(isContext);
                windowSizeSpinner.setVisible(isContext);
                frequencyLabel.setVisible(isFreq);
                searchField.setText("");
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

    public int getWindowSize() {
        return (Integer) windowSizeSpinner.getValue();
    }

    public void updateResultLabel(String text) {
        frequencyLabel.setText(text);
    }

    public void clearInputs() {
        searchField.setText("");
        frequencyLabel.setText("");
        windowSizeSpinner.setValue(2);
        operationSelector.setSelectedIndex(0);
    }

    public void addClearListener(ActionListener listener) {
        clearBtn.addActionListener(listener);
    }

    public void addOperationListener(ActionListener listener) {
        operationSelector.addActionListener(listener);
    }

    public void addWindowSizeListener(ChangeListener listener) {
        windowSizeSpinner.addChangeListener(listener);
    }

    public void addSearchInputListener(DocumentListener listener) {
        searchField.getDocument().addDocumentListener(listener);
    }
}
