package com.obiektowe.classes.Value;

public class StringValue extends Value {

    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public Value add(Value value) {
        return new StringValue(this.getInstance() + value.getInstance().toString());
    }

    @Override
    public Value sub(Value value) throws Exception {
        return null;
    }

    @Override
    public Value eq(Value value) throws Exception {
        return this.getInstance().equals(value.getInstance().toString()) ? new BooleanValue(true) : new BooleanValue(false);
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
    public StringValue create(String s) {
        return new StringValue(s);
    }

    @Override
    public String getInstance() {
        return this.value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (String)value;
    }
}
