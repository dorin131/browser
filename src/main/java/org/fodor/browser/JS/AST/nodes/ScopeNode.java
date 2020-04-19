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
            children.add(node);
        }
        return children.get(children.size() - 1);
    }

    public ArrayList<ASTNode> getChildren() {
        return children;
    }

    @Override
    public Value execute(Interpreter i) {
        return i.run(this);
    }
}
