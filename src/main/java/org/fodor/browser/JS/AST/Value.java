package org.fodor.browser.JS.AST;

public class Value<T> {
    public enum Type {
        Undefined,
        Null,
        Number,
        String,
        Object,
        Boolean,
    }

    private Type type = Type.Undefined;
    private T value;

    public Value(Type type) {
        this.type = type;
    }

    public Value(T value) {
        this.value = value;
    }

//    public Double getAsDouble() {
//        if (value instanceof Double) {
//            return (Double) value;
//        }
//        throw new RuntimeException("Value is not a Double");
//    }
//
//    public Boolean getAsBoolean() {
//        if (value instanceof Boolean) {
//            return (Boolean) value;
//        }
//        throw new RuntimeException("Value is not a Boolean");
//    }


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
}
