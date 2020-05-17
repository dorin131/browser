package org.fodor.browser.html;

import org.fodor.browser.gui.custom.Canvas;
import org.fodor.browser.shared.JSEngine;
import org.fodor.browser.shared.Renderer;

public class HTML implements Renderer {
    private JSEngine js;

    public HTML(JSEngine js) {
        this.js = js;
    }

    public void render(Canvas canvas, String html) {
        var lexer = new Tokenizer(html);
        var dom = new Parser(lexer).parse();
        canvas.paint(dom, js);
    }
}
