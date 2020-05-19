package org.fodor.browser.html;

import org.fodor.browser.html.stucts.Token;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TokenizerTest {
    @Test
    void run1() {
        String input = "<div>Hello</div>";
        Token[] expected = {
                new Token(Token.Type.OPEN_TAG, "div"),
                new Token(Token.Type.TEXT, "Hello"),
                new Token(Token.Type.CLOSE_TAG, "div"),
        };

        var tokenizer = new Tokenizer(input);

        for (int i = 0; i < expected.length; i++) {
            var token = tokenizer.next();
            assertEquals(expected[i].getType(), token.getType());
            assertEquals(expected[i].getContent(), token.getContent());
        }
    }

    @Test
    void run2() {
        String input = "<div>Hello<div>World</div></div>";
        Token[] expected = {
                new Token(Token.Type.OPEN_TAG, "div"),
                new Token(Token.Type.TEXT, "Hello"),
                new Token(Token.Type.OPEN_TAG, "div"),
                new Token(Token.Type.TEXT, "World"),
                new Token(Token.Type.CLOSE_TAG, "div"),
                new Token(Token.Type.CLOSE_TAG, "div"),
        };

        var tokenizer = new Tokenizer(input);

        for (int i = 0; i < expected.length; i++) {
            var token = tokenizer.next();
            assertEquals(expected[i].getType(), token.getType());
            assertEquals(expected[i].getContent(), token.getContent());
        }
    }

    @Test
    void run3() {
        String input = "Dorin";
        Token[] expected = {
                new Token(Token.Type.TEXT, "Dorin"),
        };

        var tokenizer = new Tokenizer(input);

        for (int i = 0; i < expected.length; i++) {
            var token = tokenizer.next();
            assertEquals(expected[i].getType(), token.getType());
            assertEquals(expected[i].getContent(), token.getContent());
        }
    }

    @Test
    void run4() {
        String input = "<div>Hello<script>1+2</script></div>";
        Token[] expected = {
                new Token(Token.Type.OPEN_TAG, "div"),
                new Token(Token.Type.TEXT, "Hello"),
                new Token(Token.Type.OPEN_TAG, "script"),
                new Token(Token.Type.TEXT, "1+2"),
                new Token(Token.Type.CLOSE_TAG, "script"),
                new Token(Token.Type.CLOSE_TAG, "div"),
        };

        var tokenizer = new Tokenizer(input);

        for (int i = 0; i < expected.length; i++) {
            var token = tokenizer.next();
            assertEquals(expected[i].getType(), token.getType());
            assertEquals(expected[i].getContent(), token.getContent());
        }
    }

    @Test
    void withAttributes() {
        String input = "<div hello=\"world\" hey=\"ho\">Hello</div>";
        Token[] expected = {
                new Token(Token.Type.OPEN_TAG, "div", new HashMap<String, String>() {
                    {
                        put("hello", "world");
                        put("hey", "ho");
                    }
                }),
                new Token(Token.Type.TEXT, "Hello"),
                new Token(Token.Type.CLOSE_TAG, "div"),
        };

        var tokenizer = new Tokenizer(input);

        for (int i = 0; i < expected.length; i++) {
            var token = tokenizer.next();
            if (i == 0) {
                assertTrue(expected[i].getAttributes().get("hello").equals("world"));
                assertTrue(expected[i].getAttributes().get("hey").equals("ho"));
            }
            assertEquals(expected[i].getType(), token.getType());
            assertEquals(expected[i].getContent(), token.getContent());
        }
    }
}