package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.Interpreter;

public class Literal extends Expression {
    private Value value;

    public Literal(Value value) {
        this.value = value;
    }

    public Value execute(Interpreter interpreter) {
        return value;
    }

    @Override
    public void dump(int indent) {
        printIndent(indent);
        System.out.println(value.getValue());
    }
}
