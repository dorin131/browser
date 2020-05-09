package org.fodor.browser.GUI.actions;

import org.fodor.browser.BrowserContext;
import org.fodor.browser.GUI.components.Console;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ConsoleKeyUpAction extends KeyAdapter {
    @Override
    public void keyReleased(KeyEvent e) {
        final int ARROW_UP = 38;
        Console console = BrowserContext.getConsole();
        String lastCommand = console.getLastCommand();

        super.keyReleased(e);
        
        if (e.getKeyCode() == ARROW_UP && e.getID() == KeyEvent.KEY_RELEASED) {
            console.getTextField().setText(lastCommand);
        }
    }
}
