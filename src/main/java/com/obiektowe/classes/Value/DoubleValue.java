package com.obiektowe.classes.Value;

import com.obiektowe.classes.Exceptions.IncompatibleValueTypes;

public class DoubleValue extends Value {

    private Double value;

    public DoubleValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public Value add(Value value) throws IncompatibleValueTypes {
        if (value instanceof IntegerValue || value instanceof DoubleValue || value instanceof FloatValue) {
            return new DoubleValue(this.getInstance() + ((Double) value.getInstance()));
        } else {
            throw new IncompatibleValueTypes("Types can not be added");
        }
    }

    @Override
    public Value sub(Value value) throws IncompatibleValueTypes {
        if (value instanceof IntegerValue || value instanceof DoubleValue || value instanceof FloatValue) {
            return new DoubleValue(this.getInstance() - ((Double) value.getInstance()));
        } else {
            throw new IncompatibleValueTypes("Types can not be subtracted");
        }
    }

    @Override
    public Value eq(Value value) throws IncompatibleValueTypes { //equals
        if (value instanceof IntegerValue || value instanceof DoubleValue || value instanceof FloatValue) {
            if (this.getInstance().equals((Double) value.getInstance())) {
                return new BooleanValue(true);
            } else {
                return new BooleanValue(false);
            }
        } else {
            throw new IncompatibleValueTypes("Types can not be compared");
        }
    }

    @Override
    public Value lte(Value value) {
        return this.getInstance() > (Double) value.getInstance() ? value : this;
    }

    @Override
    public Value gte(Value value) {
        return this.getInstance() > (Double) value.getInstance() ? this : value;
    }

    @Override
    public Value neq(Value value) throws IncompatibleValueTypes { //not equal
        if (value instanceof IntegerValue || value instanceof DoubleValue || value instanceof FloatValue) {
            if (this.getInstance().equals((Double) value.getInstance())) {
                return new BooleanValue(false);
            } else {
                return new BooleanValue(true);
            }
        } else {
            throw new IncompatibleValueTypes("Types can not be compared");
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof IntegerValue || object instanceof DoubleValue || object instanceof FloatValue) {
            if (this.getInstance().equals((Double) object)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getInstance().hashCode();
    }

    @Override
    public DoubleValue create(String s) {
        return new DoubleValue(new Double(s));
    }

    @Override
    public Double getInstance() {
        return this.getValue();
    }

    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (Double)value;
    }

}
