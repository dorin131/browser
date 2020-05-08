package org.fodor.browser.JS.AST;

import org.fodor.browser.JS.AST.nodes.BlockStatement;

public class ScopeFrame {
    BlockStatement blockStatement;

    public ScopeFrame(BlockStatement blockStatement) {
        this.blockStatement = blockStatement;
    }
}
