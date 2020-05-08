package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.JSObject;
import org.fodor.browser.JS.AST.ScopeFrame;
import org.fodor.browser.JS.AST.nodes.ASTNode;
import org.fodor.browser.shared.Value;
import org.fodor.browser.JS.AST.nodes.BlockStatement;
import org.fodor.browser.JS.AST.nodes.ReturnStatement;
import org.fodor.browser.JS.AST.nodes.ScopeNode;

import java.util.ArrayList;

public class Interpreter {
    private JSObject global;
    private ArrayList<ScopeFrame> scopeStack = new ArrayList<>();

    public Interpreter() {
        this.global = new JSObject();
    }

    public Value run(ScopeNode scopeNode) {
        boolean hasReturned = false;
        enterScope(scopeNode);

        Value lastValue = Value.jsUndefined();
        for (ASTNode node : scopeNode.getChildren()) {
            lastValue = node.execute(this);
            if (scopeNode instanceof BlockStatement && node instanceof ReturnStatement) {
                hasReturned = true;
                break;
            }
        }

        exitScope(scopeNode);

        if (scopeNode instanceof BlockStatement && !hasReturned) {
            return Value.jsUndefined();
        }
        return lastValue;
    }

    public void enterScope(ScopeNode scopeNode) {
        scopeStack.add(new ScopeFrame(scopeNode));
    }

    public void exitScope(ScopeNode scopeNode) {
        scopeStack.remove(scopeStack.size() - 1);
    }

    public JSObject getGlobal() {
        return global;
    }
}
