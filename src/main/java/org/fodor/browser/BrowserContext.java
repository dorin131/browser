package org.fodor.browser;

import org.fodor.browser.GUI.components.Console;
import org.fodor.browser.JS.JS;
import org.fodor.browser.interfaces.JSEngine;

public class BrowserContext {
    private JSEngine jsEngine = new JS();
    private Console console = new Console();

    public JSEngine getJSEngine() {
        return jsEngine;
    }

    public Console getConsole() {
        return console;
    }
}
