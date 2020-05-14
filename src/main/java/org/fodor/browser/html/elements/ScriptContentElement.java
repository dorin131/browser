package org.fodor.browser.html.elements;

import org.fodor.browser.shared.JSEngine;

public class ScriptContentElement extends ScriptElement {
    String jsContents;

    public ScriptContentElement(String contents) {
        setJsContents(contents);
    }

    @Override
    public void execute(JSEngine jsEngine) {
        // jsEngine.eval(jsContents);
    }

    public String getJsContents() {
        return jsContents;
    }

    public void setJsContents(String jsContents) {
        this.jsContents = jsContents;
    }
}
