package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.structs.JSObject;
import org.fodor.browser.JS.AST.structs.ScopeFrame;
import org.fodor.browser.JS.AST.structs.ASTNode;
import org.fodor.browser.JS.AST.statements.Program;
import org.fodor.browser.shared.Value;
import org.fodor.browser.JS.AST.statements.ReturnStatement;
import org.fodor.browser.JS.AST.statements.BlockStatement;

import java.util.ArrayList;

public class Interpreter {
    private JSObject global;
    private ArrayList<ScopeFrame> scopeStack = new ArrayList<>();
    private ArrayList<JSObject> localScopes = new ArrayList<>();

    public Interpreter() {
        this.global = new JSObject();
    }

    public Value run(BlockStatement blockStatement) {
        boolean hasReturned = false;
        enterScope(blockStatement);

        Value lastValue = Value.jsUndefined();
        for (ASTNode node : blockStatement.getChildren()) {
            lastValue = node.execute(this);
            if (!isTopLevelBlockStatement(blockStatement) && isReturnStatement(node)) {
                hasReturned = true;
                break;
            }
        }

        exitScope(blockStatement);

        if (!isTopLevelBlockStatement(blockStatement) && !hasReturned) {
            return Value.jsUndefined();
        }
        return lastValue;
    }

    private boolean isReturnStatement(ASTNode node) {
        return node instanceof ReturnStatement;
    }

    /*
    Check if these are top level statements. Meaning not wrapped in a function.
     */
    private boolean isTopLevelBlockStatement(BlockStatement blockStatement) {
        return blockStatement instanceof Program;
    }

    public void enterScope(BlockStatement blockStatement) {
        scopeStack.add(new ScopeFrame(blockStatement));
        localScopes.add(blockStatement.getLocalScope());
    }

    public void exitScope(BlockStatement blockStatement) {
        scopeStack.remove(scopeStack.size() - 1);
        localScopes.remove(localScopes.size() - 1);
    }

    public JSObject getGlobal() {
        return global;
    }

    public ArrayList<JSObject> getLocalScopes() {
        return localScopes;
    }
}
