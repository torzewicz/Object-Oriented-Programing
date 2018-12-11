package com.obiektowe.classes.Value;

import com.obiektowe.classes.Exceptions.IncompatibleValueTypes;
import org.joda.time.DateTime;

public class DateTimeValue extends Value {

    public DateTimeValue() {
    }

    private DateTime value;

    public DateTimeValue(DateTime value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public Value add(Value value) throws IncompatibleValueTypes {
        if (value instanceof DateTimeValue) {
            DateTime dateTime = ((DateTimeValue) value).getInstance();
            return new DateTimeValue(this.value.plusYears(dateTime.getYear()).plusDays(dateTime.getDayOfMonth()).plusMonths(dateTime.getMonthOfYear()));
        } else {
            throw new IncompatibleValueTypes("Types can not be added");
        }
    }

    @Override
    public Value sub(Value value) throws IncompatibleValueTypes {
        if (value instanceof DateTimeValue) {
            DateTime dateTime = ((DateTimeValue) value).getInstance();
            return new DateTimeValue(this.value.minusYears(dateTime.getYear()).minusDays(dateTime.getDayOfMonth()).minusMonths(dateTime.getMonthOfYear()));
        } else {
            throw new IncompatibleValueTypes("Types can not be subtracted");
        }
    }

    @Override
    public Value eq(Value value) throws IncompatibleValueTypes {
        if (value instanceof DateTimeValue) {
            if (this.value.equals(value.getInstance())) {
                return new BooleanValue(true);
            } else return new BooleanValue(false);
        } else {
            throw new IncompatibleValueTypes("Types can not be compared");
        }
    }

    @Override
    public Value lte(Value value) throws IncompatibleValueTypes {
        if (value instanceof DateTimeValue) {
            return this.value.isAfter(((DateTimeValue) value).getInstance()) ? new DateTimeValue(((DateTimeValue) value).getInstance()) : new DateTimeValue(this.value);
        } else {
            throw new IncompatibleValueTypes("Types can not be compared");
        }
    }

    @Override
    public Value gte(Value value) throws IncompatibleValueTypes {
        if (value instanceof DateTimeValue) {
            return this.value.isBefore(((DateTimeValue) value).getInstance()) ? new DateTimeValue(((DateTimeValue) value).getInstance()) : new DateTimeValue(this.value);
        } else {
            throw new IncompatibleValueTypes("Types can not be compared");
        }
    }

    @Override
    public Value neq(Value value) throws IncompatibleValueTypes {
        if (value instanceof DateTimeValue) {
            if (this.value.equals(value.getInstance())) {
                return new BooleanValue(false);
            } else return new BooleanValue(true);
        } else {
            throw new IncompatibleValueTypes("Types can not be compared");
        }
    }

    @Override
    public boolean equals(Object object) {
        return this.value.equals(object);
    }

    @Override
    public int hashCode() {
        return this.getInstance().hashCode();
    }

    @Override
    public DateTimeValue create(String s) {
        return new DateTimeValue(new DateTime(s));
    }

    @Override
    public DateTime getInstance() {
        return this.value;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = (DateTime)value;
    }
}
