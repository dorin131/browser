package org.fodor.browser.js.AST.statements;

import org.fodor.browser.js.AST.expressions.Identifier;
import org.fodor.browser.js.AST.structs.Token;
import org.fodor.browser.js.AST.structs.Value;
import org.fodor.browser.js.Interpreter;

import java.util.ArrayList;

public class FunctionDeclaration extends Statement {
    private Identifier name = new Identifier(new Token(Token.Type.IDENT, "anonymous"));
    private BlockStatement body;

    // Used when function declared as "function name() {};"
    // TODO: Add parameters
    public FunctionDeclaration(Identifier name, BlockStatement body) {
        this.name = name;
        this.body = body;
    }
    // Used when function declared as "var name = function() {};"
    public FunctionDeclaration(BlockStatement body, ArrayList<Identifier> parameters) {
        this.body = body;
        this.body.addParameters(parameters);
    }

    public Identifier getIdentifier() {
        return this.name;
    }

    public BlockStatement getBody() {
        return body;
    }

    public Value execute(Interpreter interpreter) {
        interpreter.getGlobal().put(getIdentifier(), getBody());

        return new Value(Value.Type.Function, getBody());
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        getBody().dump(indent + 1);
    }
}
