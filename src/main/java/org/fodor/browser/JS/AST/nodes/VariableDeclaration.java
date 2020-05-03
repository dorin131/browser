package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Token;

public class VariableDeclaration extends ASTNode {
    private Token.Type type;
    private String name;

    public VariableDeclaration(Token token) {
        this.type = token.getType();
    }

    public void setName(Token token) {
        this.name = token.getValue();
    }

    public String getName() {
        return this.name;
    }
}
