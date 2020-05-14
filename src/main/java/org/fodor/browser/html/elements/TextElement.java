package org.fodor.browser.html.elements;

import javax.swing.*;
import java.util.ArrayList;

public class TextElement extends Element {
    private String content;

    public TextElement(String text) {
        setType(ElementType.TEXT);
        setContent(text);
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public void draw(JPanel canvas) {

    }

    @Override
    public ArrayList<Element> getChildren() {
        return null;
    }
}
