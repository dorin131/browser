package org.fodor.browser.html;

import org.fodor.browser.html.stucts.Token;

import java.util.HashMap;

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
        ATTRIBUTE_NAME,
        BEFORE_ATTRIBUTE_NAME,
        AFTER_ATTRIBUTE_NAME,
        ATTRIBUTE_VALUE,
        BEFORE_ATTRIBUTE_VALUE,
        AFTER_ATTRIBUTE_VALUE,
        SELF_CLOSING_START_TAG,
    }
    private enum Character {
        TAB('\u0009'),
        LINE_FEED('\n'),
        CARRIAGE_RETURN('\r'),
        FORM_FEED('\u000B'),
        SPACE('\u0020'),
        SOLIDUS('\u002F'), // (/)
        GREATER_THAN('\u003E'),
        EQUALS_SIGN('\u003D'),
        NULL('\u0000'),
        QUOTATION_MARK('\"'),
        APOSTROPHE('\''),
        LESS_THAN_SIGN('\u003C');

        private char c;
        Character(char c) {
            this.c = c;
        }
        public char getChar() {
            return c;
        }
    }
    private State state = State.DATA;
    private String source;
    private int next = 0;
    private char ch;
    private HashMap<String, String> attributes = new HashMap<>();
    private String currentTagName = "";
    private String currentAttributeName = "";
    private String currentAttributeValue = "";
    private String textBuffer = ""; // contains text for text tags
    private boolean insideOpenTag = true; // context for tagNameState

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

    public Token next() {
        switch(getState()) {
            case DATA:
                return dataState();
            case TAG_OPEN:
                return tagOpenState();
            case TAG_NAME:
                return tagNameState();
            case END_TAG_OPEN:
                return endTagOpenState();
            case ATTRIBUTE_NAME:
                return attributeNameState();
            case BEFORE_ATTRIBUTE_NAME:
                return beforeAttributeNameState();
            case AFTER_ATTRIBUTE_NAME:
                return afterAttributeNameState();
            case ATTRIBUTE_VALUE:
                return attributeValueState();
            case BEFORE_ATTRIBUTE_VALUE:
                return beforeAttributeValueState();
            case AFTER_ATTRIBUTE_VALUE:
                return afterAttributeValueState();
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
            insideOpenTag = true;
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
            setState(State.TAG_NAME);
            insideOpenTag = false;
            return next();
        } else if (ch == 0) {
            return new Token(Token.Type.EOF, null);
        }
        consume();
        return new Token(Token.Type.ILLEGAL, "invalid-first-character-of-tag-name: " + ch);
    }

    private Token tagNameState() {
        if (isAlphaNumeric()) {
            currentTagName += ch;
            consume();
            return next();
        } else if (is(Character.SPACE)) {
            setState(State.BEFORE_ATTRIBUTE_NAME);
            consume();
            return next();
        } else if (is(Character.GREATER_THAN)) {
            var tagName = currentTagName;
            currentTagName = "";
            consume();
            setState(State.DATA);
            return new Token(insideOpenTag ? Token.Type.OPEN_TAG : Token.Type.CLOSE_TAG, tagName);
        } else if (ch == 0) {
            return new Token(Token.Type.EOF, null);
        }
        consume();
        return new Token(Token.Type.ILLEGAL, "invalid-character-in-tag-name: " + ch);
    }

    private Token beforeAttributeNameState() {
        if (is(Character.SPACE)) {
            consume();
            return next();
        } else if (is(Character.SOLIDUS) || is(Character.GREATER_THAN)) {
            setState(State.AFTER_ATTRIBUTE_NAME);
            return next();
        }
        setState(State.ATTRIBUTE_NAME);
        return next();
    }

    private Token attributeNameState() {
        if (is(Character.TAB) || is(Character.LINE_FEED) || is(Character.FORM_FEED) || is(Character.SPACE) || is(Character.SOLIDUS) || is(Character.GREATER_THAN)) {
            setState(State.AFTER_ATTRIBUTE_NAME);
            return next();
        } else if (is(Character.EQUALS_SIGN)) {
            setState(State.BEFORE_ATTRIBUTE_VALUE);
            consume();
            return next();
        } else if (isAlphaNumeric()) {
            currentAttributeName += ch;
            consume();
            return next();
        } else if (is(Character.QUOTATION_MARK) || is(Character.APOSTROPHE) || is(Character.LESS_THAN_SIGN)) {
            return new Token(Token.Type.ILLEGAL, "unexpected-character-in-attribute-name: " + ch);
        }
        currentAttributeName += ch;
        consume();
        return next();
    }

    private Token afterAttributeNameState() {
        if (is(Character.TAB) || is(Character.LINE_FEED) || is(Character.FORM_FEED) || is(Character.SPACE)) {
            consume();
            return next();
        } else if (is(Character.SOLIDUS)) {
            // TODO: Self-closing start tag state
        } else if (is(Character.EQUALS_SIGN)) {
            setState(State.BEFORE_ATTRIBUTE_VALUE);
            return next();
        } else if (is(Character.GREATER_THAN)) {
            setState(State.DATA);
            return next();
        }
        consume();
        return next();
    }

    private Token beforeAttributeValueState() {
        if (is(Character.TAB) || is(Character.LINE_FEED) || is(Character.FORM_FEED) || is(Character.SPACE)) {
            consume();
            return next();
        } else if (is(Character.QUOTATION_MARK) || is(Character.APOSTROPHE)) {
            setState(State.ATTRIBUTE_VALUE);
            consume();
            return next();
        } else if (is(Character.GREATER_THAN)) {
            return new Token(Token.Type.ILLEGAL, "missing-attribute-value parse error: " + ch);
        }
        currentAttributeValue += ch;
        consume();
        return next();
    }

    private Token attributeValueState() {
        if (is(Character.QUOTATION_MARK) || is(Character.APOSTROPHE)) {
            setState(State.AFTER_ATTRIBUTE_VALUE);
            consume();
            return next();
        }
        currentAttributeValue += ch;
        consume();
        return next();
    }

    private Token afterAttributeValueState() {
        if (is(Character.TAB) || is(Character.LINE_FEED) || is(Character.FORM_FEED) || is(Character.SPACE)) {
            storeAttribute();
            setState(State.BEFORE_ATTRIBUTE_NAME);
            return next();
        } else if (is(Character.SOLIDUS)) {
            // TODO: Self-closing start tag state
        } else if (is(Character.GREATER_THAN)) {
            storeAttribute();
            var tagName = currentTagName;
            currentTagName = "";
            consume();
            setState(State.DATA);
            return new Token(insideOpenTag ? Token.Type.OPEN_TAG : Token.Type.CLOSE_TAG, tagName, attributes);
        }
        return new Token(Token.Type.ILLEGAL, "parse error: " + ch);
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

    private void storeAttribute() {
        attributes.put(currentAttributeName, currentAttributeValue);
        currentAttributeName = "";
        currentAttributeValue = "";
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

    private boolean is(Character c) {
        return ch == c.getChar();
    }
}
