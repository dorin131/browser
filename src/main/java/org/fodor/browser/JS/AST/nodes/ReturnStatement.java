package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.Interpreter;

public class ReturnStatement extends ASTNode {
    Expression argument;

    public ReturnStatement(Expression node) {
        this.argument = node;
    }

    public Expression getArgument() {
        return argument;
    }

    @Override
    public Value execute(Interpreter i) {
        Value value = getArgument().execute(i);
        i.doReturn();
        return value;
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        getArgument().dump(indent + 1);
    }
}
