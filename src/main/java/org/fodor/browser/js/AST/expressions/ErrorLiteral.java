package org.fodor.browser.js.AST.expressions;

import org.fodor.browser.js.AST.structs.Value;

public class ErrorLiteral extends Literal {
    public ErrorLiteral(String msg) {
        super(new Value(Value.Type.Error, msg));
    }
}
