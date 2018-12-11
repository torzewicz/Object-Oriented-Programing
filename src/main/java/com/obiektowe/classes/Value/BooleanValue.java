package com.obiektowe.classes.Value;

import com.obiektowe.classes.Exceptions.IncompatibleValueTypes;

public class BooleanValue extends Value {

    public BooleanValue() {
    }

    private Boolean value;

    public BooleanValue(Boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public Value add(Value value) throws IncompatibleValueTypes {
        if (value instanceof BooleanValue) {
            if (this.getInstance().equals(true) && ((BooleanValue) value).getInstance().equals(false) || this.getInstance().equals(false) && ((BooleanValue) value).getInstance().equals(true)) {
                return new BooleanValue(true);
            } else return new BooleanValue(false);
        } else {
            throw new IncompatibleValueTypes("Types can not be added");
        }
    }

    @Override
    public Value sub(Value value) throws IncompatibleValueTypes {
        if (value instanceof BooleanValue) {
            if (this.getInstance().equals(true) && ((BooleanValue) value).getInstance().equals(true) || this.getInstance().equals(false) && ((BooleanValue) value).getInstance().equals(false)) {
                return new BooleanValue(false);
            } else return new BooleanValue(true);
        } else {
            throw new IncompatibleValueTypes("Types can not be subtracted");
        }
    }

    @Override
    public Value eq(Value value) throws IncompatibleValueTypes {
        if (value instanceof BooleanValue) {
            return this.getInstance().equals(value.getInstance()) ? new BooleanValue(true) : new BooleanValue(false);
        } else {
            throw new IncompatibleValueTypes("Types can not be compared");
        }
    }

    @Override
    public Value lte(Value value) {
        if (this.getInstance().equals(false) || ((BooleanValue) value).getInstance().equals(false)) {
            return new BooleanValue(false);
        }
        return new BooleanValue(true);
    }

    @Override
    public Value gte(Value value) {
        if (this.getInstance().equals(true) || ((BooleanValue) value).getInstance().equals(true)) {
            return new BooleanValue(true);
        }
        return new BooleanValue(false);
    }

    @Override
    public Value neq(Value value) {
        return this.getInstance().equals(value.getInstance()) ? new BooleanValue(false) : new BooleanValue(true);
    }

    @Override
    public boolean equals(Object object) {
        return this.getInstance().equals(object);
    }

    @Override
    public int hashCode() {
        return this.getInstance().hashCode();
    }

    @Override
    public BooleanValue create(String s) {
        return new BooleanValue(Boolean.valueOf(s));
    }

    @Override
    public Boolean getInstance() {
        return this.getValue();
    }

    public Boolean getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (Boolean)value;
    }

}
