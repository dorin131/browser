package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.Interpreter;

public class BinaryExpression extends Expression {
    public enum Op {
        Plus,
        Minus,
    }

    private Op op;
    private Expression lhs;
    private Expression rhs;

    public BinaryExpression(Op op, Expression lhs, Expression rhs) {
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public Value execute(Interpreter i) {
        Value<Integer> left = lhs.execute(i);
        Value<Integer> right = rhs.execute(i);
        switch (op) {
            case Plus:
                return new Value(left.getValue() + right.getValue());
            case Minus:
                return new Value(left.getValue() - right.getValue());
            default:
                throw new RuntimeException("Invalid operator");
        }
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        lhs.dump(indent + 1);
        rhs.dump(indent + 1);
    }
}
