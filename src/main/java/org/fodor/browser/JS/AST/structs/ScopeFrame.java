package org.fodor.browser.JS.AST.structs;

import org.fodor.browser.JS.AST.statements.BlockStatement;

public class ScopeFrame {
    BlockStatement blockStatement;

    public ScopeFrame(BlockStatement blockStatement) {
        this.blockStatement = blockStatement;
    }
}
