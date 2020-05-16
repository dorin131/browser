package org.fodor.browser.js.AST.expressions;

import org.fodor.browser.js.AST.structs.ASTNode;
import org.fodor.browser.js.Interpreter;
import org.fodor.browser.shared.Value;

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
    public Value execute(Interpreter i) {
        String obj = "{";
        for (Identifier key : properties.keySet()) {
            obj += String.format("\t%s : %s\n", key.getName(), properties.get(key));
        }
        obj += "}";
        return new Value(Value.Type.Object, obj);
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
    }
}
