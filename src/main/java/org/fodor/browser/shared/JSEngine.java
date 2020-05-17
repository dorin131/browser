package org.fodor.browser.shared;

import org.fodor.browser.js.AST.expressions.ObjectExpression;

public interface JSEngine {
    Value eval(String code);
    void addWindowObject(ObjectExpression object);
}
