package org.fodor.browser.gui.actions;

import org.fodor.browser.gui.MainWindow;
import org.fodor.browser.gui.components.Console;
import org.fodor.browser.interfaces.JSEngine;
import org.fodor.browser.js.AST.structs.Value;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsoleEnterAction implements ActionListener {
    private JSEngine jsEngine;
    private Console console;
    private MainWindow GUI;

    public ConsoleEnterAction(Console console, JSEngine jsEngine, MainWindow GUI) {
        this.console = console;
        this.jsEngine = jsEngine;
        this.GUI = GUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTextField inputField = GUI.getConsoleTextField();
        String input = inputField.getText();
        Value result;

        try {
            result = jsEngine.eval(input);
        } catch (Exception exception) {
            result = new Value(Value.Type.Error, exception.toString());
        }

        console.printInput(new Value(Value.Type.String, input));
        console.printOutput(result);
        setLastCommand(input);
        clearInput();
    }

    private void setLastCommand(String input) {
        console.setLastCommand(input);
    }

    private void clearInput() {
        GUI.getConsoleTextField().setText("");
    }
}
