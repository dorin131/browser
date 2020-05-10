package org.fodor.browser;

import org.fodor.browser.GUI.components.Console;
import org.fodor.browser.JS.JS;
import org.fodor.browser.shared.JSEngine;

public class BrowserContext implements Context {
    private final JSEngine jsEngine = new JS();
    private Console console = new Console(this);

    public JSEngine getJSEngine() {
        return jsEngine;
    }

    public Console getConsole() {
        return console;
    }
}
