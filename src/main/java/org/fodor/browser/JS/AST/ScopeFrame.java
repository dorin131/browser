package org.fodor.browser.JS.AST;

import org.fodor.browser.JS.AST.nodes.ScopeNode;

public class ScopeFrame {
    ScopeNode scopeNode;

    public ScopeFrame(ScopeNode scopeNode) {
        this.scopeNode = scopeNode;
    }
}
