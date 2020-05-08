package org.fodor.browser.JS.AST;

import org.fodor.browser.JS.AST.nodes.BlockStatement;

public class Function {
    private String name;
    private BlockStatement body;

    public Function(String name, BlockStatement body) {
        this.name = name;
        this.body = body;
    }

    public BlockStatement body() {
        return body;
    }

    public String name() {
        return name;
    }
}
