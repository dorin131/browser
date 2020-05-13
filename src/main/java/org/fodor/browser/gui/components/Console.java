package org.fodor.browser.gui.components;

import org.fodor.browser.gui.MainWindow;
import org.fodor.browser.shared.Value;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.util.EmptyStackException;
import java.util.Stack;

public class Console {
    private static Console uniqueInstance;
    private static final char in = '←';
    private static final char out = '→';
    private Stack<String> commandHistory = new Stack<>();
    private String prefix;
    private JTextPane textPane;
    private StyledDocument doc;

    private Console(MainWindow GUI) {
        setTextPane(GUI.getConsoleTextPane());
    }

    public synchronized static Console getInstance(MainWindow GUI) {
        if (uniqueInstance == null) {
            uniqueInstance = new Console(GUI);
        }
        return uniqueInstance;
    }

    public void printOutput(Value value) {
        checkConsoleIsSet();
        setPrefix(out);
        switch (value.getType()) {
            case String:
                printFormatted(new Color(34,139,34), value.getValue().toString());
                break;
            case Number:
                printFormatted(Color.blue, value.getValue().toString());
                break;
            case Error:
                printFormatted(Color.red, value.getValue().toString());
                break;
            default:
                printFormatted(Color.black, value.getType().toString());
        }
    }

    public void printInput (Value value) {
        checkConsoleIsSet();
        setPrefix(in);
        printFormatted(Color.black, value.getValue().toString());
    }

    private void checkConsoleIsSet() {
        if (textPane == null) {
            throw new RuntimeException("Console element not set");
        }
    }

    private String getPrefix() {
        return prefix;
    }

    private void setPrefix(char caret) {
        prefix = String.format("[%s] %s ", java.time.LocalTime.now(), caret);
    }

    private void printFormatted(Color color, String msg) {
        Style style = textPane.addStyle(null, null);
        StyleConstants.setForeground(style, color);

        try {
            doc.insertString(doc.getLength(), getPrefix(), null);
            doc.insertString(doc.getLength(), msg + "\n", style);
        } catch (BadLocationException e) {};
    }

    public String getLastCommand() {
        try {
            return commandHistory.pop();
        } catch (EmptyStackException e) {
            return "";
        }
    }

    public void setLastCommand(String lastCommand) {
        if (lastCommand.length() > 0) {
            commandHistory.push(lastCommand);
        }
    }

    private void setTextPane(JTextPane textPane) {
        this.textPane = textPane;
        this.doc = this.textPane.getStyledDocument();
    }
}
