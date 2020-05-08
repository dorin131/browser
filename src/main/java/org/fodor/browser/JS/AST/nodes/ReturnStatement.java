package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.shared.Value;
import org.fodor.browser.JS.Interpreter;

public class ReturnStatement extends Statement {
    ASTNode expression;

    public ReturnStatement(ASTNode expression) {
        this.expression = expression;
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
