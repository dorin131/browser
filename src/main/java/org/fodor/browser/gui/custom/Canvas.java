package org.fodor.browser.gui.custom;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    private String content = "hello";

    public void draw(String text) {
        this.setContent(text);
        this.repaint();
        this.getParent().revalidate(); // to update scroll bars
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
        var dimension = new Dimension();
        var lines = content.split("\n");
        var preferredWidth = 0;
        var preferredHeight = lines.length * g2d.getFontMetrics().getHeight();

        for (String line : lines) {
            g2d.drawString(line, x, y += g2d.getFontMetrics().getHeight());

            var lineWidth = g2d.getFontMetrics().stringWidth(line);
            if (lineWidth > preferredWidth) {
                preferredWidth = lineWidth;
                dimension.setSize(preferredWidth, preferredHeight);
            }
            setPreferredSize(dimension);
        }
    }
}
