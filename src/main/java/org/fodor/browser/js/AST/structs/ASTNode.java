package org.fodor.browser.js.AST.structs;

import org.fodor.browser.shared.Value;
import org.fodor.browser.js.Interpreter;

public abstract class ASTNode {
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
