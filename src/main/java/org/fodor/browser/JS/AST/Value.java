package org.fodor.browser.JS.AST;

public class Value<T> {
    public enum Type {
        Undefined,
        Null,
        Number,
        String,
        Object,
        Boolean,
        Function
    }

    private Type type = Type.Undefined;
    private T value;

    public Value(Type type) {
        this.type = type;
    }

    public Value(Type type, T value) {
        this.type = type;
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }

    static public Value jsUndefined() {
        return new Value(Type.Undefined);
    }

    static public Value jsNull() {
        return new Value(Type.Null);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Value) {
            if (((Value) o).value != null && value != null) {
                return ((Value) o).type.toString().equals(type.toString()) &&
                        ((Value) o).value.equals(value);
            } else if (((Value) o).value == null && value == null) {
                return ((Value) o).type.toString().equals(type.toString());
            }
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("{ Type: %s, Value: %s }", type, value);
    }
}
