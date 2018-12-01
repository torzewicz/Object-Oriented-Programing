package com.obiektowe.classes.Value;

import com.obiektowe.classes.Exceptions.IncompatibleValueTypes;

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
    public Value sub(Value value) throws IncompatibleValueTypes {
        if (this.getInstance().equals(value.getInstance().toString())) {
            return new StringValue("");
        } else throw new IncompatibleValueTypes("Can not subtract these StringValues");
    }

    @Override
    public Value eq(Value value) {
        return this.getInstance().equals(value.getInstance().toString()) ? new BooleanValue(true) : new BooleanValue(false);
    }

    @Override
    public Value lte(Value value) {
        return this.getInstance().length() > value.getInstance().toString().length() ? new BooleanValue(true) : new BooleanValue(false);
    }

    @Override
    public Value gte(Value value) {
        return this.getInstance().length() < value.getInstance().toString().length() ? new BooleanValue(true) : new BooleanValue(false);
    }

    @Override
    public Value neq(Value value) {
        return this.getInstance().equals(value.getInstance().toString()) ? new BooleanValue(false) : new BooleanValue(true);
    }

    @Override
    public boolean equals(Object object) {
        return this.getInstance().equals(object.toString());
    }

    @Override
    public int hashCode() {
        return this.getInstance().hashCode();
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
