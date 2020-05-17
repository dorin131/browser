package org.fodor.browser.js.AST.structs;

import java.util.HashMap;
import java.util.Map;

public class Token {
    private Type type;
    private String value;

    private static final Map<String, Type> KEYWORDS = new HashMap<>() {
        {
            put("function", Token.Type.FUNCTION);
            put("var", Token.Type.VAR);
            put("return", Token.Type.RETURN);
            put("true", Token.Type.TRUE);
            put("false", Token.Type.FALSE);
            put("if", Token.Type.IF);
            put("else", Token.Type.ELSE);
        }
    };

    public enum Type {
        /* Identifiers */
        IDENT,

        /* Literals */
        NUM,
        STR,

        /* Quotes */
        SINGLEQUOTE,
        DOUBLEQUOTE,

        /* Keywords */
        FUNCTION,
        VAR,
        RETURN,
        TRUE,
        FALSE,
        IF,
        ELSE,

        /* Operators */
        ASSIGN,
        PLUS,
        MINUS,
        SLASH,
        ASTERISK,
        GT,
        LT,
        GE,
        LE,
        EQ,
        NEQ,
        BANG,
        DOT,

        /* Separators */
        COLON,
        SEMICOLON,
        LPAREN,
        RPAREN,
        COMMA,
        LBRACE,
        RBRACE,

        /* Special */
        ILLEGAL,
        EOF
    }

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Token(Type type, char value) {
        this.type = type;
        this.value = Character.toString(value);
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public static Token.Type lookupIdent(String value) {
        String key = value.toLowerCase();
        if (KEYWORDS.containsKey(key)) {
            return KEYWORDS.get(key);
        }
        // a user defined identifier
        return Token.Type.IDENT;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Token)) {
            return false;
        }
        final Token token = (Token) o;
        if (!token.getType().toString().equals(getType().toString())) {
            return false;
        }
        if (!token.getValue().equals(getValue())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + getType().toString().hashCode();
        hash = 53 * hash + getValue().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        return String.format("{ Type: %s, Value: \"%s\" }", getType().toString(), getValue());
    }
}
