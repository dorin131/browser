package org.fodor.browser.js.AST.expressions;

import org.fodor.browser.js.AST.structs.ASTNode;
import org.fodor.browser.shared.Value;
import org.fodor.browser.js.Interpreter;

public class BinaryExpression extends Expression {
    private String op;
    private ASTNode lhs;
    private ASTNode rhs;

    public String getOp() {
        return op;
    }

    public ASTNode getLhs() {
        return lhs;
    }

    public ASTNode getRhs() {
        return rhs;
    }

    public BinaryExpression(String op, ASTNode lhs, ASTNode rhs) {
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
            case "+":
                return new Value(Value.Type.Number, leftValue + rightValue);
            case "-":
                return new Value(Value.Type.Number, leftValue - rightValue);
            case "*":
                return new Value(Value.Type.Number, leftValue * rightValue);
            case "/":
                return new Value(Value.Type.Number, leftValue / rightValue);
            default:
                throw new RuntimeException("Invalid operator");
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", lhs.toString(), getOp(), getRhs().toString());
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
