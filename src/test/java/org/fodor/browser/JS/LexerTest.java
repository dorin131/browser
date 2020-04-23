package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Token;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

class LexerTest {
    @Test
    void run() {
        String input = "function test() {\n" +
                "\treturn (1 + 2) + 3;\n" +
                "}\n" +
                "\n" +
                "test();";
        ArrayList<Token> expected = new ArrayList<>();
        expected.add(new Token(Token.Type.Keyword, Token.Keyword.function.toString()));
        expected.add(new Token(Token.Type.Identifier, "test"));
        expected.add(new Token(Token.Type.Punctuator, "("));
        expected.add(new Token(Token.Type.Punctuator, ")"));
        expected.add(new Token(Token.Type.Punctuator, "{"));
        expected.add(new Token(Token.Type.Keyword, Token.Keyword.returns.toString()));
        expected.add(new Token(Token.Type.Punctuator, "("));
        expected.add(new Token(Token.Type.Numeric, "1"));
        expected.add(new Token(Token.Type.Punctuator, "+"));
        expected.add(new Token(Token.Type.Numeric, "2"));
        expected.add(new Token(Token.Type.Punctuator, ")"));
        expected.add(new Token(Token.Type.Punctuator, "+"));
        expected.add(new Token(Token.Type.Numeric, "3"));
        expected.add(new Token(Token.Type.Punctuator, ";"));
        expected.add(new Token(Token.Type.Punctuator, "}"));
        expected.add(new Token(Token.Type.Identifier, "test"));
        expected.add(new Token(Token.Type.Punctuator, "("));
        expected.add(new Token(Token.Type.Punctuator, ")"));
        expected.add(new Token(Token.Type.Punctuator, ";"));

        ArrayList<Token> result = new Lexer(input).parse();

        assertArrayEquals(expected.toArray(), result.toArray());
    }
}