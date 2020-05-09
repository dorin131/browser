package org.fodor.browser.GUI.components;

import org.fodor.browser.GUI.actions.ConsoleEnterAction;
import org.fodor.browser.GUI.actions.ConsoleKeyUpAction;
import org.fodor.browser.shared.Value;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class Console {
    private static final char in = '←';
    private static final char out = '→';
    private String lastCommand = "";
    private String prefix;
    private JTextPane textPane;
    private JTextField textField;
    private StyledDocument doc;

    public Console(JTextPane textPane, JTextField textField) {
        this.textPane = textPane;
        this.textField = textField;
        this.doc = this.textPane.getStyledDocument();

        registerActions();
    }

    private void registerActions() {
        textField.addActionListener(new ConsoleEnterAction());
        textField.addKeyListener(new ConsoleKeyUpAction());
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

    public JTextField getTextField() {
        return textField;
    }

    public String getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(String lastCommand) {
        this.lastCommand = lastCommand;
    }
}
