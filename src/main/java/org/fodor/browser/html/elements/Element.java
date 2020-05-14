package org.fodor.browser.html.elements;

import javax.swing.*;

public abstract class Element {
    ElementType type;
    Integer width = 0;
    Integer height = 0;

    public void setType(ElementType type) {
        this.type = type;
    }

    public ElementType getType() {
        return this.type;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public abstract void draw(JPanel canvas);
}
