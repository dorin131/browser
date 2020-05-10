package org.fodor.browser;

import org.fodor.browser.GUI.components.Canvas;
import org.fodor.browser.GUI.components.Console;
import org.fodor.browser.JS.JS;
import org.fodor.browser.shared.JSEngine;

public class BrowserContext implements Context {
    private final JSEngine jsEngine = new JS();
    private Console console = new Console(this);
    private Canvas canvas = new Canvas(this);

    public JSEngine getJSEngine() {
        return jsEngine;
    }

    public Console getConsole() {
        return console;
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
