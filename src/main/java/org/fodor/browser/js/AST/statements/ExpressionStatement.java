package org.fodor.browser.js.AST.statements;

import org.fodor.browser.js.AST.structs.ASTNode;
import org.fodor.browser.shared.Value;
import org.fodor.browser.js.Interpreter;

public class ExpressionStatement extends Statement {
    ASTNode expression;

    public ExpressionStatement() {}

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
