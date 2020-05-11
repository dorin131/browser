package org.fodor.browser.GUI;

import org.fodor.browser.GUI.components.Canvas;
import org.fodor.browser.actions.GoAction;
import org.fodor.browser.shared.JSEngine;

import javax.swing.*;

public class MainWindow {
    private static MainWindow uniqueInstance = new MainWindow();
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
    private JPanel contentPanel;
    private JTextPane consoleTextPane;
    private JTabbedPane devToolsTabbedPane;
    private JPanel consoleTab;
    private JScrollPane consoleScrollPane;
    private JScrollPane contentScrollPane;
    private JPanel canvasPanel;
    private JSEngine jsEngine;

    private MainWindow() {
        this.mainFrame = new JFrame("Browser");

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

    private void createUIComponents() {
        // https://www.jetbrains.com/help/idea/creating-form-initialization-code.html
        canvasPanel = new Canvas();
    }

    public JTextField getConsoleTextField() {
        return consoleTextField;
    }

    public JTextPane getConsoleTextPane() {
        return consoleTextPane;
    }

    public static MainWindow getInstance() {
        return uniqueInstance;
    }

    public JButton getGoButton() {
        return goButton;
    }

    public JTextField getAddressField() {
        return addressField;
    }

    public JPanel getCanvasPanel() {
        return canvasPanel;
    }
}
