package org.fodor.browser.shared;

import org.fodor.browser.gui.custom.Canvas;

public interface Renderer {
    void render(Canvas canvas, String html);
}
