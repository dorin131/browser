package org.fodor.browser.GUI.actions;

import org.fodor.browser.Context;
import org.fodor.browser.GUI.components.Console;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ConsoleKeyUpAction extends KeyAdapter {
    final int ARROW_UP = 38;
    Console console;

    public ConsoleKeyUpAction(Context browserContext) {
        console = browserContext.getConsole();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);

        if (e.getKeyCode() == ARROW_UP && e.getID() == KeyEvent.KEY_RELEASED) {
            String lastCommand = console.getLastCommand();
            console.getTextField().setText(lastCommand);
        }
    }
}
