package org.fodor.browser.JS.AST;

import java.util.HashMap;

public class JSObject {
    private HashMap<String, Value> properties = new HashMap<>();

    public Value get(String propName) {
        if (properties.containsKey(propName)) {
            return properties.get(propName);
        }
        return Value.jsUndefined();
    }

    public void put(String propName, Value propValue) {
        properties.put(propName, propValue);
    }
}
