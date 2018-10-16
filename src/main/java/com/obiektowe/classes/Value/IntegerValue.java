package com.obiektowe.classes.Value;

public class IntegerValue extends Value {

    private Integer value;

    public IntegerValue(Integer value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public Value add(Value value) throws Exception {
        if (value instanceof IntegerValue || value instanceof DoubleValue || value instanceof FloatValue) {
            return new IntegerValue(this.getInstance() + ((Integer)value.getInstance()));
        } else {
            throw new Exception("Types can not be added");
        }
    }

    @Override
    public Value sub(Value value) throws Exception{
        if (value instanceof IntegerValue || value instanceof DoubleValue || value instanceof FloatValue) {
            return new IntegerValue(this.getInstance() - ((Integer)value.getInstance()));
        } else {
            throw new Exception("Types can not be subtracted");
        }
    }

    @Override
    public Value eq(Value value) throws Exception{ //equals
        if (value instanceof IntegerValue || value instanceof DoubleValue || value instanceof FloatValue) {
//            return new IntegerValue(this.getInstance() - ((Integer)value.getInstance()));
        } else {
            throw new Exception("Types can not be compared");
        }
    }

    @Override
    public Value lte(Value value) { //lesser t
        return null;
    }

    @Override
    public Value gte(Value value) { //greaer
        return null;
    }

    @Override
    public Value neq(Value value) { //not equal
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
    public IntegerValue create(String s) {
        return null;
    }

    @Override
    public Integer getInstance() {
        return this.getValue();
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


}
