package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.AST.nodes.Program;

public class JS {
    public Value eval(String code) {
        Lexer lexer = new Lexer(code);
        Program program = new Parser().parse(lexer);
        program.dump(0);
        Value result = new Interpreter().run(program);

        return result;
    }
}
