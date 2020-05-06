package org.fodor.browser.JS.AST;

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

        /* Separators */
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
        if (o instanceof Token) {
            return ((Token) o).type.toString().equals(type.toString()) && ((Token) o).value.equals(value);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("{ Type: %s, Value: \"%s\" }", type.toString(), value);
    }
}
