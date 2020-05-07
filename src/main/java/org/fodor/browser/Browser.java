package org.fodor.browser;

import org.fodor.browser.GUI.MainWindow;
import org.fodor.browser.JS.JS;
import org.fodor.browser.interfaces.JSEngine;

public class Browser {
    public static void main(String[] args) {
        System.out.println("Browser started");
        JSEngine jsEngine = new JS();
        new MainWindow(jsEngine).show();
    }
}
