package org.fodor.browser.actions;

import org.fodor.browser.GUI.MainWindow;
import org.fodor.browser.GUI.components.Console;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ConsoleKeyUpAction extends KeyAdapter {
    private final int ARROW_UP = 38;
    private Console console;
    private MainWindow GUI;

    public ConsoleKeyUpAction(Console console, MainWindow GUI) {
        this.console = console;
        this.GUI = GUI;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);

        if (e.getKeyCode() == ARROW_UP && e.getID() == KeyEvent.KEY_RELEASED) {
            String lastCommand = console.getLastCommand();
            GUI.getConsoleTextField().setText(lastCommand);
        }
    }
}
