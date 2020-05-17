package org.fodor.browser;

import org.fodor.browser.gui.MainWindow;
import org.fodor.browser.gui.components.Console;
import org.fodor.browser.html.HTML;
import org.fodor.browser.js.JS;
import org.fodor.browser.gui.actions.ConsoleEnterAction;
import org.fodor.browser.gui.actions.ConsoleKeyUpAction;
import org.fodor.browser.gui.actions.GoAction;
import org.fodor.browser.shared.JSEngine;
import org.fodor.browser.shared.Renderer;

public class Browser {
    JSEngine jsEngine;
    Console console;
    MainWindow GUI;
    Renderer renderer;

    public Browser() {
        GUI = MainWindow.getInstance();
        console = Console.getInstance(GUI);
        jsEngine = new JS();
        renderer = new HTML(jsEngine);

        GUI.getConsoleTextField().addActionListener(new ConsoleEnterAction(console, jsEngine, GUI));
        GUI.getConsoleTextField().addKeyListener(new ConsoleKeyUpAction(console, GUI));
        GUI.getGoButton().addActionListener(new GoAction(renderer, GUI));

        GUI.show();
    }
}
