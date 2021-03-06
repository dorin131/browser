package org.fodor.browser.js;

import org.fodor.browser.js.AST.expressions.ObjectExpression;
import org.fodor.browser.js.AST.structs.ScopeFrame;
import org.fodor.browser.js.AST.structs.ASTNode;
import org.fodor.browser.js.AST.statements.Program;
import org.fodor.browser.js.AST.structs.Value;
import org.fodor.browser.js.AST.statements.ReturnStatement;
import org.fodor.browser.js.AST.statements.BlockStatement;

import java.util.ArrayList;

public class Interpreter {
    private ObjectExpression global;
    private ArrayList<ScopeFrame> scopeStack = new ArrayList<>();
    private ArrayList<ObjectExpression> localScopes = new ArrayList<>();

    public Interpreter() {
        this.global = new ObjectExpression();
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

        exitScope();

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

    public void exitScope() {
        scopeStack.remove(scopeStack.size() - 1);
        localScopes.remove(localScopes.size() - 1);
    }

    public ObjectExpression getGlobal() {
        return global;
    }

    public ArrayList<ObjectExpression> getLocalScopes() {
        return localScopes;
    }
}
