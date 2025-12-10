package ui;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {
    private JComboBox<String> operationSelector;
    private JTextField searchField;
    private JLabel frequencyLabel;
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
        String[] options = {"Select Operation...", "Word Frequency", "Other Operation"};
        operationSelector = new JComboBox<>(options);
        operationSelector.setFont(Theme.FONT_NORMAL);

        searchField = new JTextField(10);
        searchField.setFont(Theme.FONT_NORMAL);

        frequencyLabel = new JLabel("");
        frequencyLabel.setForeground(Theme.HIGHLIGHT_COLOR);
        frequencyLabel.setFont(new Font("Arial", Font.BOLD, 14));

        clearBtn = Theme.createButton("CLEAR", Color.GRAY);
    }

    private void setupLayout() {
        dynamicInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        dynamicInputPanel.setBackground(Theme.BG_COLOR);
        dynamicInputPanel.setVisible(false);

        JLabel searchLabel = new JLabel("Target Word:");
        searchLabel.setForeground(Color.LIGHT_GRAY);

        dynamicInputPanel.add(searchLabel);
        dynamicInputPanel.add(searchField);
        dynamicInputPanel.add(frequencyLabel);

        add(operationSelector);
        add(dynamicInputPanel);
        add(clearBtn);
    }

    private void setupInternalLogic() {
        operationSelector.addActionListener(e -> {
            String selected = (String) operationSelector.getSelectedItem();
            boolean showSearch = "Word Frequency".equals(selected);
            dynamicInputPanel.setVisible(showSearch);
            if(showSearch) {
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

    public void updateResultLabel(String text) {
        frequencyLabel.setText(text);
    }

    public void clearInputs() {
        searchField.setText("");
        frequencyLabel.setText("");
    }

    public void addClearListener(ActionListener listener) {
        clearBtn.addActionListener(listener);
    }

    public void addSearchInputListener(DocumentListener listener) {
        searchField.getDocument().addDocumentListener(listener);
    }
}
