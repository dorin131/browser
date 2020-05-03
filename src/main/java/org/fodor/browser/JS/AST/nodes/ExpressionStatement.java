package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.Interpreter;

public class ExpressionStatement extends Expression {
    ASTNode expression;

    public ExpressionStatement() {}

    public ExpressionStatement(ASTNode node) {
        this.expression = node;
    }

    public ASTNode getExpression() {
        return expression;
    }

    public void setExpression(ASTNode expression) {
        this.expression = expression;
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
