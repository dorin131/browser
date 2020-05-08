package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.shared.Value;
import org.fodor.browser.JS.Interpreter;

import java.util.ArrayList;

/*
A node that has children, basically a shell or {}
 */
public class BlockStatement extends Statement {
    private ArrayList<ASTNode> children = new ArrayList<>();

    public ASTNode append(ASTNode node) {
        if (node != null) {
            children.add(node);
        }
        if (children.size() > 0) {
            return children.get(children.size() - 1);
        }
        return null;
    }

    public ArrayList<ASTNode> getChildren() {
        return children;
    }

    @Override
    public Value execute(Interpreter i) {
        return i.run(this);
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        for (ASTNode child : getChildren()) {
            child.dump(indent + 1);
        }
    }
}
