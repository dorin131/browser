package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.Interpreter;

public class ExpressionStatement extends ExpressionNode {
    ASTNode expression;

    public ExpressionStatement(ASTNode node) {
        this.expression = node;
    }

    public ASTNode getExpression() {
        return expression;
    }

    @Override
    public Value execute(Interpreter i) {
        return getExpression().execute(i);
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        getExpression().dump(indent + 1);
    }
}
