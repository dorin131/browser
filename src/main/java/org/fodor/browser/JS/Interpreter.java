package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.JSObject;
import org.fodor.browser.JS.AST.ScopeFrame;
import org.fodor.browser.JS.AST.nodes.ASTNode;
import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.AST.nodes.ScopeNode;

import java.util.ArrayList;

public class Interpreter {
    private JSObject global;
    private ArrayList<ScopeFrame> scopeStack = new ArrayList<>();

    public Interpreter() {
        this.global = new JSObject();
    }

    public Value run(ScopeNode program) {
        enterScope(program);

        Value lastValue = Value.jsUndefined();
        for (ASTNode node : program.getChildren()) {
            lastValue = node.execute(this);
        }

        exitScope(program);
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

    public void doReturn() {
        System.out.println("FIXME: Implement doReturn()");
    }
}
