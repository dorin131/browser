package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.AST.nodes.Program;
import org.fodor.browser.interfaces.JSEngine;

public class JS implements JSEngine {
    private Interpreter interpreter = new Interpreter();

    public Value eval(String code) {
        Lexer lexer = new Lexer(code);
        Program program = new Parser(lexer).parseProgram();
        // program.dump(0);
        Value result = interpreter.run(program);

        return result;
    }
}
