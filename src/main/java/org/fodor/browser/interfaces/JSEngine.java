package org.fodor.browser.interfaces;

import org.fodor.browser.JS.AST.Value;

public interface JSEngine {
    Value eval(String code);
}
