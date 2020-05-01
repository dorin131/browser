package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Operator;
import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.Interpreter;

public class BinaryExpression extends ExpressionNode {
    private Operator op;
    private ASTNode lhs;
    private ASTNode rhs;

    public Operator getOp() {
        return op;
    }

    public ASTNode getLhs() {
        return lhs;
    }

    public ASTNode getRhs() {
        return rhs;
    }

    public BinaryExpression(Operator op, ASTNode lhs, ASTNode rhs) {
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public Value execute(Interpreter i) {
        Value left = lhs.execute(i);
        Value right = rhs.execute(i);
        int leftValue;
        int rightValue;

        if (left.getValue() instanceof Integer) {
            leftValue = (Integer) left.getValue();
        } else {
            leftValue = Integer.parseInt((String) left.getValue());
        }
        if (right.getValue() instanceof Integer) {
            rightValue = (Integer) right.getValue();
        } else {
            rightValue = Integer.parseInt((String) right.getValue());
        }

        switch (op) {
            case Add:
                return new Value(Value.Type.Number, leftValue + rightValue);
            case Sub:
                return new Value(Value.Type.Number, leftValue - rightValue);
            default:
                throw new RuntimeException("Invalid operator");
        }
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        printIndent(indent + 1);
        System.out.println(op);
        lhs.dump(indent + 1);
        rhs.dump(indent + 1);
    }
}
