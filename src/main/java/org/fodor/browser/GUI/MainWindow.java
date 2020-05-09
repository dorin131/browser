package org.fodor.browser.GUI;

import org.fodor.browser.BrowserContext;
import org.fodor.browser.GUI.actions.ConsoleEnterAction;
import org.fodor.browser.GUI.actions.ConsoleKeyUpAction;
import org.fodor.browser.GUI.actions.GoAction;
import org.fodor.browser.GUI.components.Console;
import org.fodor.browser.shared.JSEngine;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
        browserContext.setConsole(new Console(consoleTextPane, consoleTextField));

        // Actions
        goButton.addActionListener(new GoAction());

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
