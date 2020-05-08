package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Token;
import org.fodor.browser.shared.Value;
import org.fodor.browser.JS.Interpreter;

public class Identifier extends ASTNode {
    String name;

    public Identifier(Token token) {
        this.name = token.getValue();
    }

    public String getName() {
        return name;
    }

    public Value execute(Interpreter interpreter) {
        return interpreter.getGlobal().get(name).execute(interpreter);
    }
}
