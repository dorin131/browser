package org.fodor.browser.html.elements;

import org.fodor.browser.shared.JSEngine;

import javax.swing.*;

public abstract class ScriptElement extends Element {
    @Override
    public void draw(JPanel canvas) {
        // Not visible;
    }

    public abstract void execute(JSEngine jsEngine);
}
