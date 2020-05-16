package org.fodor.browser.js.AST.statements;

import org.fodor.browser.js.AST.expressions.Identifier;
import org.fodor.browser.js.AST.structs.ASTNode;
import org.fodor.browser.js.AST.structs.Token;
import org.fodor.browser.shared.Value;
import org.fodor.browser.js.Interpreter;

public class VariableDeclaration extends Statement {
    private Token.Type type;
    private Identifier identifier;
    private ASTNode body;

    public VariableDeclaration(Token token) {
        this.type = token.getType();
    }

    public void setIdentifier(Identifier token) {
        this.identifier = token;
    }

    public void setBody(ASTNode contents) {
        this.body = contents;
    }

    public ASTNode getBody() {
        return body;
    }

    public Identifier getIdentifier() {
        return this.identifier;
    }

    public Value execute(Interpreter interpreter) {
        interpreter.getGlobal().put(identifier, body);

        return new Value(Value.Type.Undefined);
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        printIndent(indent + 1);
        System.out.println(identifier.getName());
        if (body != null) {
            body.dump(indent + 1);
        }
    }
}
