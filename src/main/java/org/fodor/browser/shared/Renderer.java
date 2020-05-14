package org.fodor.browser.shared;

import javax.swing.*;

public interface Renderer {
    void render(JPanel canvas, String html);
}
