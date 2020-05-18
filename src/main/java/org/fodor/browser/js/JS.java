package org.fodor.browser.js;

import org.fodor.browser.js.AST.expressions.Identifier;
import org.fodor.browser.js.AST.expressions.ObjectExpression;
import org.fodor.browser.js.AST.structs.Token;
import org.fodor.browser.js.AST.structs.Value;
import org.fodor.browser.interfaces.JSEngine;

public class JS implements JSEngine {
    private Interpreter interpreter = new Interpreter();

    public Value eval(String code) {
        var lexer = new Lexer(code);
        var program = new Parser(lexer).parseProgram();
        //program.dump(0);
        return interpreter.run(program);
    }

    public void addGlobalObject(String name, ObjectExpression exp) {
        var global = interpreter.getGlobal();
        global.put(new Identifier(new Token(Token.Type.IDENT, name)), exp);
    }
}
