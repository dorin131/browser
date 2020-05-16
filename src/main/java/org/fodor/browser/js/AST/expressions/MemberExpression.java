package org.fodor.browser.js.AST.expressions;

import org.fodor.browser.js.Interpreter;
import org.fodor.browser.shared.Value;

public class MemberExpression extends Expression {
    private Identifier object;
    private Expression property;

    public MemberExpression(Identifier object, Expression property) {
        this.object = object;
        this.property = property;
    }

    @Override
    public Value execute(Interpreter i) {
        var currentObject = (ObjectExpression) findValueInScope(i, object);
        return currentObject.get((Identifier) property).execute(i);
    }
}
