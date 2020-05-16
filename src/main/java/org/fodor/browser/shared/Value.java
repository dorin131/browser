package org.fodor.browser.shared;

import org.fodor.browser.js.AST.structs.Token;

public class Value<T> {
    public enum Type {
        Error,
        Undefined,
        Null,
        Number,
        String,
        Object,
        Boolean,
        Function
    }

    private Type type;
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

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof Value)) {
            return false;
        }
        final Value value = (Value) o;
        if (!value.getType().toString().equals(getType().toString())) {
            return false;
        }
        if (getValue() == null && value.getValue() != null) {
            return false;
        }
        if (getValue() != null && value.getValue() == null) {
            return false;
        }
        if (getValue() == null && value.getValue() == null) {
            return true;
        }
        if (!value.getValue().equals(getValue())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (getType() == null ? 0 : getType().toString().hashCode());
        hash = 53 * hash + (getValue() == null ? 0 : getValue().hashCode());
        return hash;
    }

    @Override
    public String toString() {
        return String.format("{ Type: %s, Value: %s }", type, value);
    }
}
