package org.fodor.browser.JS.AST;

import java.util.HashMap;
import java.util.Map;

public class Token {
    private Type type;
    private String value;

    private static final Map<String, Type> keywords = new HashMap<>() {
        {
            put("function", Token.Type.Function);
            put("let", Token.Type.Let);
            put("return", Token.Type.Return);
        }
    };

    public enum Type {
        Identifier,
        Punctuator,
        Numeric,
        String,

        Illegal,

        Function,
        Let,
        Return,

        Assign,
        Semicolon,
        LParen,
        RParen,
        Comma,
        Plus,
        LBrace,
        RBrace,
        EOF
    }

    public enum Keyword {
        function("function"),
        returns("return");

        public final String label;

        Keyword(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return this.label;
        }
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
        if (keywords.containsKey(value)) {
            return keywords.get(value);
        }
        // a user defined identifier
        return Token.Type.Identifier;
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
