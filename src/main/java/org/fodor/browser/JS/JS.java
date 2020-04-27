package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Token;
import org.fodor.browser.JS.AST.Value;
import org.fodor.browser.JS.AST.nodes.Program;

import java.util.ArrayList;

public class JS {
    public Value eval(String code) {
        ArrayList<Token> tokens = new Lexer(code).parse();
        Program program = new Parser(tokens).parse();
        program.dump(0);
        Value result = new Interpreter().run(program);

        return result;
    }
}
