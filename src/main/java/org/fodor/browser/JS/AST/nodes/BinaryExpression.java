package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.Interpreter;

public class BinaryExpression extends Expression {
    public enum Op {
        Add,
        Sub,
        Mul,
        Div,
        Gr,
        Geq,
        Le,
        Leq,
        Mod
    }

    private Op op;
    private ASTNode lhs;
    private ASTNode rhs;

    public Op getOp() {
        return op;
    }

    public ASTNode getLhs() {
        return lhs;
    }

    public ASTNode getRhs() {
        return rhs;
    }

    public BinaryExpression(Op op, ASTNode lhs, ASTNode rhs) {
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public Value execute(Interpreter i) {
        Value left = lhs.execute(i);
        Value right = rhs.execute(i);
        // TODO: if one is string, make both strings
        // right now we just assume we always get numbers
        int leftValue = (Integer) left.getValue();
        int rightValue = (Integer) right.getValue();

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
