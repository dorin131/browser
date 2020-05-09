package org.fodor.browser;

import org.fodor.browser.GUI.components.Console;
import org.fodor.browser.JS.JS;
import org.fodor.browser.shared.JSEngine;

public class BrowserContext {
    private static final JSEngine jsEngine = new JS();
    private static Console console;

    public static JSEngine getJSEngine() {
        return jsEngine;
    }

    public static Console getConsole() {
        return console;
    }

    public static void setConsole(Console c) {
        console = c;
    }
}
