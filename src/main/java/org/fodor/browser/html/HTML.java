package org.fodor.browser.html;

import org.fodor.browser.shared.Renderer;

import javax.swing.*;

public class HTML implements Renderer {
    public void render(JPanel canvas, String html) {
        var lexer = new Tokenizer(html);
        var dom = new Parser(lexer).parse();
        new Interpreter().run(dom);
    }
}
