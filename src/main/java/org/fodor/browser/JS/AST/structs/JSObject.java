package org.fodor.browser.JS.AST.structs;

import org.fodor.browser.JS.AST.structs.ASTNode;

import java.util.HashMap;

public class JSObject extends ASTNode {
    private HashMap<String, ASTNode> properties = new HashMap<>();

    public ASTNode get(String propName) {
        if (properties.containsKey(propName)) {
            return properties.get(propName);
        }
        return new ASTNode();
    }

    public void put(String propName, ASTNode propValue) {
        properties.put(propName, propValue);
    }
}
