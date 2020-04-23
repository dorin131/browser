package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Function;
import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.Interpreter;

public class FunctionDeclaration extends ASTNode {
    private String name = "anonymous";
    private ScopeNode body;

    public FunctionDeclaration(ScopeNode body) {
        this.body = body;
    }

    public FunctionDeclaration(String name, ScopeNode body) {
        this.name = name;
        this.body = body;
    }

    public String getName() {
        return this.name;
    }

    public ScopeNode getBody() {
        return body;
    }

    public Value execute(Interpreter interpreter) {
        Function function = new Function(name, body);
        Value value = new Value<>(function);
        interpreter.getGlobal().put(name, value);

        return value;
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        getBody().dump(indent + 1);
    }
}
