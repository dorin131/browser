package org.fodor.browser.GUI;

import org.fodor.browser.BrowserContext;
import org.fodor.browser.GUI.actions.ConsoleEnter;
import org.fodor.browser.GUI.actions.Go;
import org.fodor.browser.shared.JSEngine;

import javax.swing.*;

public class MainWindow {
    private JFrame mainFrame;
    private JPanel mainPanel;
    private JTextField addressField;
    private JButton goButton;
    private JPanel topPanel;
    private JPanel bottomPanel;
    private JLabel statusLabel;
    private JPanel middlePanel;
    private JPanel consolePanel;
    private JSplitPane splitPane;
    private JTextField consoleTextField;
    private JPanel canvasPanel;
    private JTextPane consoleTextPane;
    private JTabbedPane devToolsTabbedPane;
    private JPanel consoleTab;
    private JScrollPane consoleScrollPane;
    private JSEngine jsEngine;

    public MainWindow(BrowserContext browserContext) {
        this.mainFrame = new JFrame("Browser");

        // Setting console element
        browserContext.getConsole().setUIElement(consoleTextPane);

        // Actions
        goButton.addActionListener(new Go());
        consoleTextField.addActionListener(new ConsoleEnter(browserContext, this));

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

    public JTextField getConsoleTextField() {
        return consoleTextField;
    }
}
