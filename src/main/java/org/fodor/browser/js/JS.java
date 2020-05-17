package org.fodor.browser.js;

import org.fodor.browser.js.AST.expressions.ObjectExpression;
import org.fodor.browser.shared.Value;
import org.fodor.browser.shared.JSEngine;

public class JS implements JSEngine {
    private Interpreter interpreter = new Interpreter();

    public Value eval(String code) {
        var lexer = new Lexer(code);
        var program = new Parser(lexer).parseProgram();
        //program.dump(0);
        return interpreter.run(program);
    }

    public void addWindowObject(ObjectExpression object) {
        interpreter.addToGlobal(object);
    }
}
