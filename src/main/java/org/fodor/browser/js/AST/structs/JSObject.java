package org.fodor.browser.js.AST.structs;

import java.util.HashMap;

public class JSObject extends ASTNode {
    private HashMap<String, ASTNode> properties = new HashMap<>();

    public ASTNode get(String propName) {
        return properties.get(propName);
    }

    public void put(String propName, ASTNode propValue) {
        properties.put(propName, propValue);
    }
}
