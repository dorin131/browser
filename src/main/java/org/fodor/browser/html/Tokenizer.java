package org.fodor.browser.html;

import org.fodor.browser.html.stucts.Token;

/**
 * More info: https://html.spec.whatwg.org/multipage/parsing.html
 */
public class Tokenizer {
    private enum State {
        DATA,
        TEXT,
        TAG_OPEN, // "<"
        END_TAG_OPEN, // "/"
        TAG_NAME, // alphanumeric
        END_TAG_NAME,
        SELF_CLOSING_START_TAG,
    }
    private State state = State.DATA;
    private String source;
    private int next = 0;
    private char ch;
    private String currentTagName = "";
    private String textBuffer = "";

    public Tokenizer(String source) {
        this.source = source;
        consume();
    }

    private void consume() {
        if (next >= source.length()) {
            ch = 0;
        } else {
            ch = source.charAt(next);
        }
        next++;
    }

    private void consumeWhitespace() {
        while (ch == ' ' || ch == '\n' || ch == '\t' || ch == '\r') {
            consume();
        }
    }

    public Token next() {
        consumeWhitespace();

        switch(getState()) {
            case DATA:
                return dataState();
            case TAG_OPEN:
                return tagOpenState();
            case TAG_NAME:
                return tagNameState(false);
            case END_TAG_NAME:
                return tagNameState(true);
            case END_TAG_OPEN:
                return endTagOpenState();
            case TEXT:
            default:
                return textState();
        }
    }

    private Token dataState() {
        if (ch == '<') {
            setState(State.TAG_OPEN);
            consume();
            return next();
        } else if (ch == 0) {
            return new Token(Token.Type.EOF, null);
        }
        setState(State.TEXT);
        return next();
    }

    private Token tagOpenState() {
        if (isAlpha()) {
            setState(State.TAG_NAME);
            return next();
        } else if (ch == '/') {
            setState(State.END_TAG_OPEN);
            consume();
            return next();
        } else if (ch == 0) {
            return new Token(Token.Type.EOF, null);
        }
        consume();
        return new Token(Token.Type.ILLEGAL, "invalid-first-character-of-tag-name: " + ch);
    }

    private Token endTagOpenState() {
        if (isAlpha()) {
            setState(State.END_TAG_NAME);
            return next();
        } else if (ch == 0) {
            return new Token(Token.Type.EOF, null);
        }
        consume();
        return new Token(Token.Type.ILLEGAL, "invalid-first-character-of-tag-name: " + ch);
    }

    private Token tagNameState(boolean isEndTag) {
        if (isAlphaNumeric()) {
            currentTagName += ch;
            consume();
            return next();
        } else if (ch == '>') {
            var tagName = currentTagName;
            currentTagName = "";
            consume();
            setState(State.DATA);
            return new Token(isEndTag ? Token.Type.CLOSE_TAG : Token.Type.OPEN_TAG, tagName);
        } else if (ch == 0) {
            return new Token(Token.Type.EOF, null);
        }
        consume();
        return new Token(Token.Type.ILLEGAL, "invalid-character-in-tag-name: " + ch);
    }

    private Token textState() {
        if (ch == '<') {
            setState(State.TAG_OPEN);
            consume();
            var text = textBuffer;
            textBuffer = "";
            return new Token(Token.Type.TEXT, text);
        } else if (ch == 0) {
            setState(State.DATA);
            var text = textBuffer;
            textBuffer = "";
            return new Token(Token.Type.TEXT, text);
        }
        textBuffer += ch;
        consume();
        return next();
    }

    private State getState() {
        return state;
    }

    private void setState(State state) {
        this.state = state;
    }

    private boolean isAlpha() {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z') || (ch == '_');
    }

    private boolean isNumeric() {
        return ch >= '0' && ch <= '9';
    }

    private boolean isAlphaNumeric() {
        return isAlpha() || isNumeric();
    }
}
