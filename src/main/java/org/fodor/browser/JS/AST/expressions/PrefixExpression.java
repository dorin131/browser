package org.fodor.browser.JS.AST.expressions;

import org.fodor.browser.JS.AST.structs.ASTNode;

public class PrefixExpression extends Expression {
    private String operator;
    private ASTNode right;

    public PrefixExpression(String operator) {
        this.operator = operator;
    }

    public void setRight(ASTNode right) {
        this.right = right;
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        printIndent(indent + 1);
        System.out.println(operator);
        right.dump(indent + 1);
    }
}
