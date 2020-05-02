package org.fodor.browser.JS.AST;

public class Token {
    private Type type;
    private String value;

    public enum Type {
        Keyword,
        Identifier,
        Punctuator,
        Numeric,
        String
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

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
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
