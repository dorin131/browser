package org.fodor.browser.html.stucts;

import java.util.HashMap;

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
    private HashMap<String, String> attributes;

    public Token(Type type, String content) {
        this.type = type;
        this.content = content;
    }

    public Token(Type type, String content, HashMap<String, String> attributes) {
        this.type = type;
        this.content = content;
        this.attributes = attributes;
    }

    public Type getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public HashMap<String, String> getAttributes() {
        return attributes;
    }
}
