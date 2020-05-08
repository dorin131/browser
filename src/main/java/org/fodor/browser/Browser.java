package org.fodor.browser;

import org.fodor.browser.GUI.MainWindow;

public class Browser {
    public static void main(String[] args) {
        System.out.println("Browser started");

        new MainWindow(new BrowserContext()).show();
    }
}
