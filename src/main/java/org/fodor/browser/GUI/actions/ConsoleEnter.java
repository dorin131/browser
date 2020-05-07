package org.fodor.browser.GUI.actions;

import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.interfaces.JSEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsoleEnter implements ActionListener {
    private JSEngine jsEngine;
    private JTextField inputField;

    public ConsoleEnter(JSEngine jsEngine, JTextField inputField) {
        this.jsEngine = jsEngine;
        this.inputField = inputField;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = inputField.getText();
        Value result = jsEngine.eval(input);

        // TODO: emit message to console and clear field
        System.out.println("> " + result);
    }
}
