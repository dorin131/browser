package org.fodor.browser.GUI.actions;

import org.fodor.browser.BrowserContext;
import org.fodor.browser.Context;
import org.fodor.browser.GUI.MainWindow;
import org.fodor.browser.GUI.components.Console;
import org.fodor.browser.shared.Value;
import org.fodor.browser.shared.JSEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsoleEnterAction implements ActionListener {
    private Context browserContext;
    private Console console;

    public ConsoleEnterAction(Context browserContext) {
        this.browserContext = browserContext;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        console = browserContext.getConsole();
        JTextField inputField = console.getTextField();
        String input = inputField.getText();
        Value result;

        try {
            result = browserContext.getJSEngine().eval(input);
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
        console.getTextField().setText("");
    }
}
