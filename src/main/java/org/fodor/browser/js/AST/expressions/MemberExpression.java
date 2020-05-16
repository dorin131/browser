package org.fodor.browser.js.AST.expressions;

import org.fodor.browser.js.AST.structs.ASTNode;
import org.fodor.browser.js.Interpreter;
import org.fodor.browser.shared.Value;

public class MemberExpression extends Expression {
    private Identifier object;
    private ASTNode property;

    public MemberExpression(Identifier object, ASTNode property) {
        this.object = object;
        this.property = property;
    }

    @Override
    public Value execute(Interpreter i) {
        var currentObject = (ObjectExpression) findValueInScope(i, object);
        return currentObject.get((Identifier) property).execute(i);
    }

    @Override
    public void dump(int indent) {
        printIndent(indent);
        System.out.println(this.getClass().getSimpleName());
        object.dump(indent + 1);
        property.dump(indent + 1);
    }
}
