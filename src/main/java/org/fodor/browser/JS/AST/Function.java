package org.fodor.browser.JS.AST;

import org.fodor.browser.JS.AST.nodes.ScopeNode;

public class Function extends JSObject {
    private String name;
    private ScopeNode body;

    public Function(String name, ScopeNode body) {
        this.name = name;
        this.body = body;
    }

    public ScopeNode body() {
        return body;
    }

    public String name() {
        return name;
    }
}
