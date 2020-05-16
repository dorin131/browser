package org.fodor.browser.js.AST.expressions;

import org.fodor.browser.js.AST.structs.ASTNode;

import java.util.HashMap;

public class ObjectExpression extends Expression {
    private HashMap<String, ASTNode> properties = new HashMap<>();

    public ASTNode get(String propName) {
        return properties.get(propName);
    }

    public void put(String propName, ASTNode propValue) {
        properties.put(propName, propValue);
    }
}
