package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Function;
import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.Interpreter;

public class CallExpression extends ExpressionNode {
    private String name;

    public CallExpression(String name) {
        this.name = name;
    }

    @Override
    public Value execute(Interpreter interpreter) {
        Value callee = interpreter.getGlobal().get(this.name);
        if (callee.getValue() instanceof Function) {
            return interpreter.run(((Function) callee.getValue()).body());
        }
        throw new RuntimeException("Not Function: " + callee);
    }

    public String name() {
        return this.name;
    }

    @Override
    public void dump(int indent) {
        printIndent(indent);
        System.out.printf("%s \"%s\"\n", this.getClass().getSimpleName(), name());
    }
}