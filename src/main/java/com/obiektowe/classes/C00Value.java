package com.obiektowe.classes;

import com.obiektowe.classes.Value.Value;

final public class C00Value extends Value {

    public int getIndex() {
        return index;
    }

    public Object getValue() {
        return value;
    }

    final private int index;
    final private Object value;

    public C00Value(int index, Object value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Object: " + this.value.toString() + ", Index: " + this.index;
    }

    @Override
    public Value add(Value value) throws Exception {
        return null;
    }

    @Override
    public Value sub(Value value) throws Exception {
        return null;
    }

    @Override
    public Value eq(Value value) throws Exception {
        return null;
    }

    @Override
    public Value lte(Value value) throws Exception {
        return null;
    }

    @Override
    public Value gte(Value value) throws Exception {
        return null;
    }

    @Override
    public Value neq(Value value) throws Exception {
        return null;
    }

    @Override
    public boolean equals(Object object) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public <T> T create(String s) {
        return null;
    }

    @Override
    public <T> T getInstance() {
        return null;
    }
}