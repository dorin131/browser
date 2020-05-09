package org.fodor.browser.JS.AST.expressions;

import org.fodor.browser.JS.AST.structs.ASTNode;
import org.fodor.browser.JS.AST.structs.JSObject;
import org.fodor.browser.JS.AST.structs.Token;
import org.fodor.browser.shared.Value;
import org.fodor.browser.JS.Interpreter;

import java.util.ArrayList;

public class Identifier extends Expression {
    String name;

    public Identifier(Token token) {
        this.name = token.getValue();
    }

    public String getName() {
        return name;
    }

    public Value execute(Interpreter interpreter) {
        ASTNode value = findValue(interpreter, name);
        if (value == null) {
            return Value.jsUndefined();
        }
        return value.execute(interpreter);
    }

    private ASTNode findValue(Interpreter interpreter, String identifierName) {
        ArrayList<JSObject> localScopes = interpreter.getLocalScopes();
        // Walking the local scopes in reverse order
        for (int i = localScopes.size() - 1; i >= 0; i--) {
            ASTNode value = localScopes.get(i).get(identifierName);
            if (value != null) {
                return value;
            }
        }
        // if nothing found in local scopes, check global scope
        return interpreter.getGlobal().get(name);
    }
}
