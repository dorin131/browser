package org.fodor.browser.JS.AST.expressions;

import org.fodor.browser.JS.AST.structs.Token;
import org.fodor.browser.shared.Value;
import org.fodor.browser.JS.Interpreter;

public class Identifier extends Expression {
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
