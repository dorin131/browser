package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.Interpreter;

import java.util.ArrayList;

/*
A node that has children, basically a shell or {}
 */
public class ScopeNode extends ASTNode {
    private ArrayList<ASTNode> children = new ArrayList<>();

    /*
    Adds a node to the block scope and return last
     */
    public ASTNode append(ASTNode ...nodes) {
        for (ASTNode node : nodes) {
            if (node != null) {
                children.add(node);
            }
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
