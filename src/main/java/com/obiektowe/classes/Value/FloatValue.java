package com.obiektowe.classes.Value;

public class FloatValue extends Value {

    private Float value;

    public FloatValue(Float value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public Value add(Value value) throws Exception {
        if (value instanceof IntegerValue || value instanceof DoubleValue || value instanceof FloatValue) {
            return new FloatValue(this.getInstance() + ((Float) value.getInstance()));
        } else {
            throw new Exception("Types can not be added");
        }
    }

    @Override
    public Value sub(Value value) throws Exception {
        if (value instanceof IntegerValue || value instanceof DoubleValue || value instanceof FloatValue) {
            return new FloatValue(this.getInstance() - ((Float) value.getInstance()));
        } else {
            throw new Exception("Types can not be subtracted");
        }
    }

    @Override
    public Value eq(Value value) throws Exception { //equals
        if (value instanceof IntegerValue || value instanceof DoubleValue || value instanceof FloatValue) {
            if (this.getInstance().equals((Float) value.getInstance())) {
                return new BooleanValue(true);
            } else {
                return new BooleanValue(false);
            }
        } else {
            throw new Exception("Types can not be compared");
        }
    }

    @Override
    public Value lte(Value value) {
        return this.getInstance() > (Float) value.getInstance() ? value : this;
    }

    @Override
    public Value gte(Value value) {
        return this.getInstance() > (Float) value.getInstance() ? this : value;
    }

    @Override
    public Value neq(Value value) throws Exception { //not equal
        if (value instanceof IntegerValue || value instanceof DoubleValue || value instanceof FloatValue) {
            if (this.getInstance().equals((Float) value.getInstance())) {
                return new BooleanValue(false);
            } else {
                return new BooleanValue(true);
            }
        } else {
            throw new Exception("Types can not be compared");
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof IntegerValue || object instanceof DoubleValue || object instanceof FloatValue) {
            if (this.getInstance().equals((Float) object)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public FloatValue create(String s) {
        return new FloatValue(new Float(s));
    }

    @Override
    public Float getInstance() {
        return this.getValue();
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
