package com.obiektowe.classes.Value;

public abstract class Value {

    @Override
    public abstract String toString();

    public abstract Value add(Value value) throws Exception;

    public abstract Value sub(Value value) throws Exception;

    public abstract Value eq(Value value) throws Exception;

    public abstract Value lte(Value value) throws Exception;

    public abstract Value gte(Value value) throws Exception;

    public abstract Value neq(Value value) throws Exception;

    @Override
    public abstract boolean equals(Object object);

    @Override
    public abstract int hashCode();

    public abstract <T> T create(String s);

    public abstract <T> T getInstance();

}
