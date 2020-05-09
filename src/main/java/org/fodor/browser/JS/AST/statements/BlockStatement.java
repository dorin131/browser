package org.fodor.browser.JS.AST.statements;

import org.fodor.browser.JS.AST.expressions.Identifier;
import org.fodor.browser.JS.AST.structs.ASTNode;
import org.fodor.browser.JS.AST.structs.JSObject;
import org.fodor.browser.shared.Value;
import org.fodor.browser.JS.Interpreter;

import java.util.ArrayList;

/*
A node that has children, basically a shell or {}
 */
public class BlockStatement extends Statement {
    private JSObject localScope = new JSObject();
    private ArrayList<Identifier> parameters = new ArrayList<>();
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

    public void addParameters(ArrayList<Identifier> parameters) {
        this.parameters = parameters;
    }

    public void associateArguments(ArrayList<ASTNode> arguments) {
        for (int i = 0; i < this.parameters.size(); i++) {
            localScope.put(this.parameters.get(i).getName(), arguments.get(i));
        }
    }

    public JSObject getLocalScope() {
        return localScope;
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