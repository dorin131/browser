package org.fodor.browser.GUI.components;

import javax.swing.*;

public class Console {
    private JTextArea uiElement;

    public void setUIElement(JTextArea el) {
        this.uiElement = el;
    }

    public void print(String msg) throws RuntimeException {
        if (uiElement == null) {
            throw new RuntimeException("Console element not set");
        }
        uiElement.append("\n> " + msg);
    }
}
