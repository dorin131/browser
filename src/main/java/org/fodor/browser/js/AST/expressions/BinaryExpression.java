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

        // Coerce right to String
        if (left.getType() == Value.Type.String && right.getType() == Value.Type.Number) {
            right = new Value(Value.Type.String, right.getValue().toString());
            return stringArithmetic(op, left, right);
        }

        // Coerce left to String
        if (left.getType() == Value.Type.Number && right.getType() == Value.Type.String) {
            left = new Value(Value.Type.String, left.getValue().toString());
            return stringArithmetic(op, left, right);
        }

        if (left.getType() == Value.Type.String && right.getType() == Value.Type.String) {
            return stringArithmetic(op, left, right);
        }

        return integerArithmetic(op, left, right);
    }

    private Value stringArithmetic(String op, Value left, Value right) {
        if (!op.equals("+")) {
            throw new RuntimeException("Invalid operator");
        }
        return new Value(Value.Type.String, String.format("%s%s", left.getValue(), right.getValue()));
    }

    private Value integerArithmetic(String op, Value left, Value right) {
        int leftValue = (int) left.getValue();
        int rightValue = (int) right.getValue();
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
