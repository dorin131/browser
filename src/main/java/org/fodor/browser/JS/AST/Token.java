package org.fodor.browser.JS.AST;

import java.util.HashMap;
import java.util.Map;

public class Token {
    private Type type;
    private String value;

    private static final Map<String, Type> KEYWORDS = new HashMap<>() {
        {
            put("function", Token.Type.FUNCTION);
            put("let", Token.Type.LET);
            put("return", Token.Type.RETURN);
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
        LET,
        RETURN,

        /* Operators */
        ASSIGN,
        ADD,
        SUB,
        DIV,
        MUL,
        GT,
        LT,

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
        if (KEYWORDS.containsKey(value)) {
            return KEYWORDS.get(value);
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
