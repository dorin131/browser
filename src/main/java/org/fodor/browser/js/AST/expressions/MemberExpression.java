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

    /**
     * A bit of a mind bender.
     * Narrowing down into the object while property is a MemberExpression
     * Eventually ending with a one level object in scopeObject, from where we get our value.
     * @param i
     * @return
     */
    @Override
    public Value execute(Interpreter i) {
        var currentProperty = this.property;
        var currentObject = this.object;
        var scopeObject = (ObjectExpression) findValueInScope(i, object);
        while (!(currentProperty instanceof Identifier)) {
            currentObject = ((MemberExpression) currentProperty).object;
            currentProperty = ((MemberExpression) currentProperty).property;
            scopeObject = (ObjectExpression) scopeObject.get(currentObject);
        }
        var res = scopeObject.get((Identifier) currentProperty).execute(i);
        return res;
    }

    @Override
    public void dump(int indent) {
        printIndent(indent);
        System.out.println(this.getClass().getSimpleName());
        object.dump(indent + 1);
        property.dump(indent + 1);
    }
}
