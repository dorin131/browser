package org.fodor.browser.JS.AST.nodes;

public class IfStatement extends Expression {
    ASTNode test;
    ASTNode consequent;
    ASTNode alternate;

    public void setTest(ASTNode test) {
        this.test = test;
    }

    public void setConsequent(ASTNode consequent) {
        this.consequent = consequent;
    }

    public void setAlternate(ASTNode alternate) {
        this.alternate = alternate;
    }

    public ASTNode getTest() {
        return test;
    }

    public ASTNode getConsequent() {
        return consequent;
    }

    public ASTNode getAlternate() {
        return alternate;
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        test.dump(indent + 1);
        consequent.dump(indent + 1);
        if (alternate != null) {
            alternate.dump(indent + 1);
        }
    }
}
