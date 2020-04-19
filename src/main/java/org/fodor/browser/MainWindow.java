package org.fodor.browser;

import javax.swing.*;

public class MainWindow {
    public static void show() {
        JFrame frame = new JFrame();
        JButton button = new JButton("Go");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(button);
        frame.setSize(300, 300);
        frame.setVisible(true);
    }
}
