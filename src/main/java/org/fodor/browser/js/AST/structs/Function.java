package org.fodor.browser.js.AST.structs;

import org.fodor.browser.js.AST.statements.BlockStatement;

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
