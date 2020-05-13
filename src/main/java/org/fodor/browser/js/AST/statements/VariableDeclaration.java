package org.fodor.browser.js.AST.statements;

import org.fodor.browser.js.AST.structs.ASTNode;
import org.fodor.browser.js.AST.structs.Token;
import org.fodor.browser.shared.Value;
import org.fodor.browser.js.Interpreter;

public class VariableDeclaration extends Statement {
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
