package org.fodor.browser.js.AST.expressions;

import org.fodor.browser.js.AST.structs.ASTNode;

import java.util.HashMap;

public class ObjectExpression extends Expression {
    private HashMap<Identifier, ASTNode> properties = new HashMap<>();

    public ASTNode get(Identifier identifier) {
        return properties.get(identifier);
    }

    public void put(Identifier identifier, ASTNode propValue) {
        properties.put(identifier, propValue);
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
    }
}
