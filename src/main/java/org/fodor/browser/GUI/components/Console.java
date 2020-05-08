package org.fodor.browser.GUI.components;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

public class Console {
    private JTextPane uiElement;

    public void setUIElement(JTextPane el) {
        this.uiElement = el;
    }

    public void print(String msg) throws RuntimeException {
        if (uiElement == null) {
            throw new RuntimeException("Console element not set");
        }
        StyledDocument doc = uiElement.getStyledDocument();
        SimpleAttributeSet keyWord = new SimpleAttributeSet();

        // Formatting
        msg = String.format("[%s] > %s\n", java.time.LocalTime.now(), msg);

        try {
            doc.insertString(doc.getLength(), msg, keyWord);
        } catch (Exception e) {
            System.out.println("Failed to print to console");
        }

    }
}
