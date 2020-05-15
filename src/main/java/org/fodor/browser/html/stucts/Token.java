package org.fodor.browser.html.stucts;

public class Token {
    public enum Type {
        OPEN_TAG,
        CLOSE_TAG,
        SELF_CLOSE_TAG,
        TEXT,
        ATTRIBUTE, // ???
        EOF,
        ILLEGAL,
    }
    private Type type;
    private String content;

    public Token(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }
}
