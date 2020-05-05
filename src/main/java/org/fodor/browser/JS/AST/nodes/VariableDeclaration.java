package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Token;

public class VariableDeclaration extends ASTNode {
    private Token.Type type;
    private String name;
    private String value;

    public VariableDeclaration(Token token) {
        this.type = token.getType();
    }

    public void setName(Token token) {
        this.name = token.getValue();
    }

    public void setValue(Token value) {
        this.value = value.getValue();
    }

    public String getName() {
        return this.name;
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        printIndent(indent + 1);
        System.out.printf("\"%s = %s\"\n", name, value);
    }
}
