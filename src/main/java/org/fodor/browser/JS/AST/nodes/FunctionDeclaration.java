package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.shared.Value;
import org.fodor.browser.JS.Interpreter;

import java.util.ArrayList;

public class FunctionDeclaration extends ASTNode {
    private String name = "anonymous";
    private ArrayList<ASTNode> parameters = new ArrayList<>();
    private ScopeNode body;

    public FunctionDeclaration(String name, ScopeNode body) {
        this.name = name;
        this.body = body;
    }

    public FunctionDeclaration(ScopeNode body, ArrayList<ASTNode> parameters) {
        this.body = body;
        this.parameters = parameters;
    }

    public String getName() {
        return this.name;
    }

    public ScopeNode getBody() {
        return body;
    }

    public Value execute(Interpreter interpreter) {
        interpreter.getGlobal().put(getName(), getBody());

        return new Value(Value.Type.Undefined);
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        getBody().dump(indent + 1);
    }
}
