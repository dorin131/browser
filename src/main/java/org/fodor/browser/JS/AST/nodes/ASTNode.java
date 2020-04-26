package org.fodor.browser.JS.AST.nodes;

import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.Interpreter;

public class ASTNode {
    public Value execute(Interpreter i) {
        return Value.jsUndefined();
    }

    public void printIndent(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("  ");
        }
    }

    public void dump(int indent) {
        printIndent(indent);
        System.out.println(this.getClass().getSimpleName());
    }
}
