package org.fodor.browser.JS;

import org.fodor.browser.JS.AST.Token;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

class LexerTest {
    @Test
    void run() {
        String input = "function test() {\n" +
                "\treturn (1 + 2) - 3 * 10 / 3 > 1 < 2;\n" +
                "\t== != <= >=" +
                "}\n" +
                "\n" +
                "test();";

        Token[] expected = {
                new Token(Token.Type.FUNCTION, "function"),
                new Token(Token.Type.IDENT, "test"),
                new Token(Token.Type.LPAREN, "("),
                new Token(Token.Type.RPAREN, ")"),
                new Token(Token.Type.LBRACE, "{"),
                new Token(Token.Type.RETURN, "return"),
                new Token(Token.Type.LPAREN, "("),
                new Token(Token.Type.NUM, "1"),
                new Token(Token.Type.ADD, "+"),
                new Token(Token.Type.NUM, "2"),
                new Token(Token.Type.RPAREN, ")"),
                new Token(Token.Type.SUB, "-"),
                new Token(Token.Type.NUM, "3"),
                new Token(Token.Type.MUL, "*"),
                new Token(Token.Type.NUM, "10"),
                new Token(Token.Type.DIV, "/"),
                new Token(Token.Type.NUM, "3"),
                new Token(Token.Type.GT, ">"),
                new Token(Token.Type.NUM, "1"),
                new Token(Token.Type.LT, "<"),
                new Token(Token.Type.NUM, "2"),
                new Token(Token.Type.SEMICOLON, ";"),
                new Token(Token.Type.EQ, "=="),
                new Token(Token.Type.NEQ, "!="),
                new Token(Token.Type.LE, "<="),
                new Token(Token.Type.GE, ">="),
                new Token(Token.Type.RBRACE, "}"),
                new Token(Token.Type.IDENT, "test"),
                new Token(Token.Type.LPAREN, "("),
                new Token(Token.Type.RPAREN, ")"),
                new Token(Token.Type.SEMICOLON, ";"),
        };

        Lexer l = new Lexer(input);
        Token token;

        for (int i = 0; (token = l.nextToken()).getType() != Token.Type.EOF; i++) {
            assertEquals(expected[i], token);
        }
    }
}