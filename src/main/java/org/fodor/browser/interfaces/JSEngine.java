package org.fodor.browser.interfaces;

import org.fodor.browser.js.AST.expressions.ObjectExpression;
import org.fodor.browser.js.AST.structs.Value;

public interface JSEngine {
    Value eval(String code);
    void addGlobalObject(String name, ObjectExpression expression);
}
