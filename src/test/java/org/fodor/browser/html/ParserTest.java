package org.fodor.browser.html;

import org.fodor.browser.html.elements.DOM;
import org.fodor.browser.html.elements.ElementType;
import org.fodor.browser.html.elements.TextElement;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void parse() {
        String input = "<div>Hello</div>";
//        Token[] input = {
//                new Token(Token.Type.OPEN_TAG, "div"),
//                new Token(Token.Type.TEXT, "Hello"),
//                new Token(Token.Type.CLOSE_TAG, "div"),
//        };

        Tokenizer tokenizer = new Tokenizer(input);
        DOM dom = new Parser(tokenizer).parse();

        assertEquals(1, dom.getChildren().size());
        var div = dom.getChildren().get(0);
        assertEquals(ElementType.DIV, div.getType());
        var text = div.getChildren().get(0);
        assertEquals(ElementType.TEXT, text.getType());
        assertEquals("Hello", ((TextElement) text).getContent());
    }
}