package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Token;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class LexerTest {
    @Test
    void run() {
        String input = "function test() {\n" +
                "\treturn (1 + 2) + 3;\n" +
                "}\n" +
                "\n" +
                "test();";

        Token[] expected = {
                new Token(Token.Type.Function, "function"),
                new Token(Token.Type.Identifier, "test"),
                new Token(Token.Type.LParen, "("),
                new Token(Token.Type.RParen, ")"),
                new Token(Token.Type.LBrace, "{"),
                new Token(Token.Type.Return, "return"),
                new Token(Token.Type.LParen, "("),
                new Token(Token.Type.Numeric, "1"),
                new Token(Token.Type.Plus, "+"),
                new Token(Token.Type.Numeric, "2"),
                new Token(Token.Type.RParen, ")"),
                new Token(Token.Type.Plus, "+"),
                new Token(Token.Type.Numeric, "3"),
                new Token(Token.Type.Semicolon, ";"),
                new Token(Token.Type.RBrace, "}"),
                new Token(Token.Type.Identifier, "test"),
                new Token(Token.Type.LParen, "("),
                new Token(Token.Type.RParen, ")"),
                new Token(Token.Type.Semicolon, ";"),
        };

        Lexer l = new Lexer(input);
        Token token;

        for (int i = 0; (token = l.nextToken()).getType() != Token.Type.EOF; i++) {
            assertEquals(expected[i], token);
        }
    }
}