package org.fodor.browser;

import org.fodor.browser.GUI.components.Canvas;
import org.fodor.browser.GUI.components.Console;
import org.fodor.browser.shared.JSEngine;

public interface Context {
    JSEngine getJSEngine();
    Console getConsole();
    Canvas getCanvas();
}
