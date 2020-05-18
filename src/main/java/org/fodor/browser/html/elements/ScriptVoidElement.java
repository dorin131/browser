package org.fodor.browser.html.elements;

import org.fodor.browser.interfaces.JSEngine;

public class ScriptVoidElement extends ScriptElement {
    String src;

    public ScriptVoidElement(String source) {
        setSrc(source);
    }

    @Override
    public void execute(JSEngine jsEngine) {
        // make HTTP request to get source
        // evaluate JS
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
