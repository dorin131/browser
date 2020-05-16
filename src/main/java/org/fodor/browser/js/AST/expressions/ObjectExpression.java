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
        return new Value(Value.Type.Object, this.toString());
    }

    @Override
    public void dump(int indent) {
        super.dump(indent);
        printIndent(indent + 1);
        System.out.println(this.toString());
    }

    @Override
    public String toString() {
        String obj = "{ ";
        for (Identifier key : properties.keySet()) {
            obj += String.format("%s: %s, ", key.getName(), properties.get(key));
        }
        obj += "}";
        return obj;
    }
}
