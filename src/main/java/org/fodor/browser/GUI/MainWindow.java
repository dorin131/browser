package org.fodor.browser.GUI;

import org.fodor.browser.GUI.components.Canvas;
import org.fodor.browser.GUI.components.Top;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    JFrame frame;
    Top top;
    Canvas canvas;

    public MainWindow() {
        frame = new JFrame();
        top = new Top();
        canvas = new Canvas();

        frame.setTitle("Browser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(top);
        frame.getContentPane().add(canvas);

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        frame.setSize(800, 600);
        frame.setMinimumSize(new Dimension(250, 250));
    }

    public void show() {
        frame.setVisible(true);
    }
}
