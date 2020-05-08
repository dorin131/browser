package org.fodor.browser.JS.AST.statements;

import org.fodor.browser.JS.AST.structs.ASTNode;
import org.fodor.browser.shared.Value;
import org.fodor.browser.JS.Interpreter;

import java.util.ArrayList;

public class FunctionDeclaration extends Statement {
    private String name = "anonymous";
    private ArrayList<ASTNode> parameters = new ArrayList<>();
    private BlockStatement body;

    public FunctionDeclaration(String name, BlockStatement body) {
        this.name = name;
        this.body = body;
    }

    public FunctionDeclaration(BlockStatement body, ArrayList<ASTNode> parameters) {
        this.body = body;
        this.parameters = parameters;
    }

    public String getName() {
        return this.name;
    }

    public BlockStatement getBody() {
        return body;
    }

    public Value execute(Interpreter interpreter) {
        interpreter.getGlobal().put(getName(), getBody());

        return new Value(Value.Type.Function);
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        getBody().dump(indent + 1);
    }
}
