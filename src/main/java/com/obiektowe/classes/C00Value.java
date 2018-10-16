package com.obiektowe.classes;

final public class C00Value {

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

}