package org.fodor.browser.GUI;

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

    public MainWindow() {
       this.mainFrame = new JFrame("Browser");
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "hello");
            }
        });
    }

    public void show() {
        this.mainFrame.setContentPane(new MainWindow().mainPanel);
        this.mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.mainFrame.pack();
        this.mainFrame.setVisible(true);
    }
}
