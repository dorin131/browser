package org.fodor.browser.html.elements;

public abstract class Element {
    ElementType type;
    String contents;

    public void setType(ElementType type) {
        this.type = type;
    }

    public ElementType getType() {
        return this.type;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getContents() {
        return this.contents;
    }
}
