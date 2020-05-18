package org.fodor.browser.js.AST.expressions;

import org.fodor.browser.js.AST.structs.Value;
import org.fodor.browser.js.Interpreter;

public class Literal extends Expression {
    private Value value;

    public Literal(Value value) {
        this.value = value;
    }

    public Value getValue() {
        return value;
    }

    public Value execute(Interpreter interpreter) {
        return value;
    }

    @Override
    public void dump(int indent) {
        printIndent(indent);
        System.out.println(value.getValue());
    }

    @Override
    public String toString() {
        return getValue().getValue().toString();
    }
}
