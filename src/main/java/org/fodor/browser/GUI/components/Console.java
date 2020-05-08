package org.fodor.browser.GUI.components;

import org.fodor.browser.shared.Value;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

public class Console {
    private JTextPane uiElement;

    public void setUIElement(JTextPane el) {
        this.uiElement = el;
    }

    public void print(Value value) throws RuntimeException {
        if (uiElement == null) {
            throw new RuntimeException("Console element not set");
        }
        StyledDocument doc = uiElement.getStyledDocument();
        SimpleAttributeSet keyWord = new SimpleAttributeSet();

        try {
            doc.insertString(doc.getLength(), formatValue(value), keyWord);
        } catch (Exception e) {
            System.out.println("Failed to print to console");
        }

    }

    private String prefix() {
        return String.format("[%s] >", java.time.LocalTime.now());
    }

    private String suffix() {
        return "\n";
    }

    private String composeMessage(String msg) {
        return prefix() + msg + suffix();
    }

    private String formatValue(Value value) {
        switch (value.getType()) {
            case String:
                return composeMessage(value.toString());
            default:
                return composeMessage(value.toString());
        }
    }
}
