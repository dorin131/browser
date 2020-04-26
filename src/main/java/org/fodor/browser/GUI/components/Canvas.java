package org.fodor.browser.GUI.components;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.pink);
        g.fillRect(20, 50, 100, 100);
        this.setBackground(Color.white);
    }
}
