package org.fodor.browser.GUI;

import org.fodor.browser.GUI.actions.ConsoleEnter;
import org.fodor.browser.GUI.actions.Go;
import org.fodor.browser.interfaces.JSEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JTextField addressField;
    private JButton goButton;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel statusLabel;
    private JPanel middlePanel;
    private JTextArea consoleTextArea;
    private JPanel consolePanel;
    private JSplitPane splitPane;
    private JTextField consoleTextField;
    private JPanel canvasPanel;
    private JSEngine jsEngine;

    public MainWindow(JSEngine jsEngine) {
        this.jsEngine = jsEngine;
        this.mainFrame = new JFrame("Browser");

        // Actions
        goButton.addActionListener(new Go());
        consoleTextField.addActionListener(new ConsoleEnter(jsEngine, consoleTextField));

        // Make console pane of fixed height
        this.splitPane.setResizeWeight(1);
    }

    public void show() {
        this.mainFrame.setContentPane(this.mainPanel);
        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.mainFrame.pack();
        this.mainFrame.setVisible(true);
    }
}
