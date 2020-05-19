package org.fodor.browser.html.elements;

import javax.swing.*;
import java.util.HashMap;

public class DivElement extends Element {
    public DivElement() {
        setType(ElementType.DIV);
    }

    public DivElement(HashMap<String, String> attributes) {
        super(attributes);
        setType(ElementType.DIV);
    }

    @Override
    public void draw(JPanel canvas) {
        // TODO: implement
    }
}
