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

public class Minimum implements Applyable {
    @Override
    public DataFrame apply(DataFrame dataFrame) throws WrongInsertionTypeException {


        List<Col> newCols = new ArrayList<>();

        for (Col col : dataFrame.getCols()) {

            if (col.getObjects().get(0) instanceof DoubleValue || col.getObjects().get(0) instanceof FloatValue || col.getObjects().get(0) instanceof IntegerValue) {

                Double min = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> (Double) i).min(Comparator.comparing(Double::valueOf)).get();
                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(min);
                newCols.add(colToAdd);
            } else if (col.getObjects().get(0) instanceof BooleanValue) {
                Object min = col.getObjects().stream().map(i -> ((Value) i).getInstance()).filter(i -> i.equals(true)).findFirst().get();
                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(min);
                newCols.add(colToAdd);
            } else if (col.getObjects().get(0) instanceof StringValue) {
                int min = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> i.toString().length()).min(Comparator.comparing(Integer::valueOf)).get();
                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(min);
                newCols.add(colToAdd);
            } else {
                DateTime max = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> (DateTime) i).min(Comparator.comparing(DateTime::toDate)).get();
                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(max);
                newCols.add(colToAdd);
            }
        }

        return new DataFrame(newCols);

    }
}
