package org.fodor.browser.GUI.actions;

import org.fodor.browser.BrowserContext;
import org.fodor.browser.GUI.MainWindow;
import org.fodor.browser.GUI.components.Console;
import org.fodor.browser.shared.Value;
import org.fodor.browser.shared.JSEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConsoleEnter implements ActionListener {
    private JSEngine jsEngine;
    private Console console;
    private JTextField inputField;

    public ConsoleEnter(BrowserContext browserContext, MainWindow mainWindow) {
        this.jsEngine = browserContext.getJSEngine();
        this.console = browserContext.getConsole();
        this.inputField = mainWindow.getConsoleTextField();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = inputField.getText();
        Value result;
        String printable;

        try {
            result = jsEngine.eval(input);
            printable = result.toString();
        } catch (Exception exception) {
            printable = exception.toString();
        }

        console.print(printable);
    }
}
