package com.obiektowe.classes.Applyable;

import com.obiektowe.classes.Col;
import com.obiektowe.classes.DataFrame;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;
import com.obiektowe.classes.Interfaces.Applyable;
import com.obiektowe.classes.Value.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Sum implements Applyable {
    @Override
    public DataFrame apply(DataFrame dataFrame) throws WrongInsertionTypeException {

        List<Col> newCols = new ArrayList<>();


        for (Col col : dataFrame.getCols()) {

            if (col.getObjects().get(0) instanceof DoubleValue || col.getObjects().get(0) instanceof FloatValue || col.getObjects().get(0) instanceof IntegerValue) {

                Double sum = col.getObjects().stream().map(i -> ((Value) i).getInstance()).mapToDouble(i -> new Double(i.toString())).sum();
                Col colToAdd = new Col(col.getName(), "DoubleValue");
                colToAdd.add(new DoubleValue(sum));
                newCols.add(colToAdd);
            } else if (col.getObjects().get(0) instanceof BooleanValue) {
                Long trues = col.getObjects().stream().map(i -> ((Value) i).getInstance()).filter(i -> i.equals(true)).count();
                Long falses = col.getObjects().stream().map(i -> ((Value) i).getInstance()).filter(i -> i.equals(false)).count();
                Boolean sum = trues >= falses;
                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(new BooleanValue(sum));
                newCols.add(colToAdd);
            } else if (col.getObjects().get(0) instanceof StringValue) {
                Integer max = col.getObjects().stream().map(i -> ((Value) i).getInstance()).mapToInt(i -> i.toString().length()).sum();
                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(new StringValue(max.toString()));
                newCols.add(colToAdd);
            } else {
                Long sum = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> (DateTime) i).mapToLong(DateTime::getMillis).sum();
                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(new DateTimeValue(new DateTime(sum)));
                newCols.add(colToAdd);
            }
        }

        return new DataFrame(newCols);
    }
}
