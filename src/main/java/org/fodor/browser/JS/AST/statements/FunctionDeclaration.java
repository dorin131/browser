package org.fodor.browser.JS.AST.statements;

import org.fodor.browser.JS.AST.expressions.Identifier;
import org.fodor.browser.JS.AST.structs.ASTNode;
import org.fodor.browser.shared.Value;
import org.fodor.browser.JS.Interpreter;

import java.util.ArrayList;

public class FunctionDeclaration extends Statement {
    private String name = "anonymous";
    private BlockStatement body;

    // Used when function declared as "function name() {};"
    // TODO: Add parameters
    public FunctionDeclaration(String name, BlockStatement body) {
        this.name = name;
        this.body = body;
    }
    // Used when function declared as "var name = function() {};"
    public FunctionDeclaration(BlockStatement body, ArrayList<Identifier> parameters) {
        this.body = body;
        this.body.addParameters(parameters);
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
