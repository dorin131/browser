package org.fodor.browser.js.AST.expressions;

import org.fodor.browser.js.AST.structs.ASTNode;
import org.fodor.browser.js.AST.structs.Token;
import org.fodor.browser.shared.Value;
import org.fodor.browser.js.Interpreter;

import java.util.ArrayList;

public class Identifier extends Expression {
    String name;

    public Identifier(Token token) {
        this.name = token.getValue();
    }

    public String getName() {
        return name;
    }

    public Value execute(Interpreter interpreter) {
        ASTNode value = findValueInScope(interpreter, this);
        if (value == null) {
            return Value.jsUndefined();
        }
        return value.execute(interpreter);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Identifier)) {
            return false;
        }
        final Identifier identifier = (Identifier) obj;

        if (!this.getName().equals(identifier.getName())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 53 * 3 + this.getName().hashCode();
    }

    @Override
    public String toString() {
        return String.format("{ Identifier: %s }", this.getName());
    }
}
