package org.fodor.browser.js.AST.expressions;

import org.fodor.browser.js.AST.structs.ASTNode;
import org.fodor.browser.js.Interpreter;

import java.util.ArrayList;

public class Expression extends ASTNode {
    public ASTNode findValueInScope(Interpreter interpreter, Identifier identifier) {
        ArrayList<ObjectExpression> localScopes = interpreter.getLocalScopes();
        // Walking the local scopes in reverse order
        for (int i = localScopes.size() - 1; i >= 0; i--) {
            ASTNode value = localScopes.get(i).get(identifier);
            if (value != null) {
                return value;
            }
        }
        // if nothing found in local scopes, check global scope
        return interpreter.getGlobal().get(identifier);
    }
}
