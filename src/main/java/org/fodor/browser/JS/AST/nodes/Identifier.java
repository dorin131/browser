package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Token;
import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.Interpreter;

public class Identifier extends ASTNode {
    String name;

    public Identifier(Token token) {
        this.name = token.getValue();
    }

    // TODO: implement
    public Value execute(Interpreter interpreter) {
        return null;
    }
}
