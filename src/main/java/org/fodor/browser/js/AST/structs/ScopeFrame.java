package org.fodor.browser.js.AST.structs;

import org.fodor.browser.js.AST.statements.BlockStatement;

public class ScopeFrame {
    BlockStatement blockStatement;

    public ScopeFrame(BlockStatement blockStatement) {
        this.blockStatement = blockStatement;
    }
}
