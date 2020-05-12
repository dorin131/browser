package org.fodor.browser;

import org.fodor.browser.GUI.MainWindow;
import org.fodor.browser.GUI.components.Canvas;
import org.fodor.browser.GUI.components.Console;
import org.fodor.browser.JS.JS;
import org.fodor.browser.actions.ConsoleEnterAction;
import org.fodor.browser.actions.ConsoleKeyUpAction;
import org.fodor.browser.actions.GoAction;
import org.fodor.browser.shared.JSEngine;

public class Browser {
    JSEngine jsEngine;
    Console console;
    MainWindow GUI;

    public Browser() {
        GUI = MainWindow.getInstance();
        console = Console.getInstance(GUI);
        jsEngine = new JS();

        GUI.getConsoleTextField().addActionListener(new ConsoleEnterAction(console, jsEngine, GUI));
        GUI.getConsoleTextField().addKeyListener(new ConsoleKeyUpAction(console, GUI));
        GUI.getGoButton().addActionListener(new GoAction(GUI));

        GUI.show();
    }
}
