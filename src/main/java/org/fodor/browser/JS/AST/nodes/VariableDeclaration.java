package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Function;
import org.fodor.browser.JS.AST.Token;
import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.Interpreter;

public class VariableDeclaration extends ASTNode {
    private Token.Type type;
    private String name;
    private ASTNode body;

    public VariableDeclaration(Token token) {
        this.type = token.getType();
    }

    public void setName(Token token) {
        this.name = token.getValue();
    }

    public void setBody(ASTNode contents) {
        this.body = contents;
    }

    public ASTNode getBody() {
        return body;
    }

    public String getName() {
        return this.name;
    }

    public Value execute(Interpreter interpreter) {
        interpreter.getGlobal().put(name, body);

        return new Value(Value.Type.Undefined);
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        printIndent(indent + 1);
        System.out.println(name);
        body.dump(indent + 1);
    }
}
