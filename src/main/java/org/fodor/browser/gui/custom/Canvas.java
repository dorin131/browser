package org.fodor.browser.gui.custom;

import org.fodor.browser.html.elements.DOM;
import org.fodor.browser.html.elements.Element;
import org.fodor.browser.html.elements.ElementType;
import org.fodor.browser.html.elements.TextElement;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {
    private DOM dom;
    private String text = "";

    private void refresh() {
        this.repaint();
        this.getParent().revalidate(); // to update scroll bars
    }

    public void paint(DOM dom) {
        setDom(dom);
        refresh();
    }

    public void setDom(DOM dom) {
        this.dom = dom;
    }

    private String getText() {
        return this.text;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = (Graphics2D) g;
        this.drawContent(g2d, 0, 0);
    }

    private void drawContent(Graphics2D g2d, int x, int y) {
        if (dom == null) {
            return;
        }
        resetText();
        getTextFromDOM(dom);

        var dimension = new Dimension();
        var lines = getText().split("\n");
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

    private void resetText() {
        this.text = "";
    }

    private void getTextFromDOM(Element dom) {
        for (Element el : dom.getChildren()) {
            if (el.getType() == ElementType.TEXT) {
                text += ((TextElement) el).getContent() + '\n';
            } else {
                getTextFromDOM(el);
            }
        }
    }
}
