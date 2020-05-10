package org.fodor.browser.GUI.components;

import org.fodor.browser.Context;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    private Context browserContext;
    private String content = "";

    public Canvas(Context browserContext) {
        this.browserContext = browserContext;
    }

    public void draw(String text) {
        this.setContent(text);
        this.repaint();
    }

    private void setContent(String text) {
        this.content = text;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = (Graphics2D) g;
        this.drawContent(g2d, 0, 0);
    }

    private void drawContent(Graphics2D g2d, int x, int y) {
        for (String line : content.split("\n")) {
            g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());
        }
    }
}
