package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Token;

public class Identifier extends ASTNode {
    String name;

    public Identifier(Token token) {
        this.name = token.getValue();
    }
}
