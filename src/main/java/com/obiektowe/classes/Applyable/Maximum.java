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

public class Maximum implements Applyable {
    @Override
    public DataFrame apply(DataFrame dataFrame) throws WrongInsertionTypeException {

        List<Col> newCols = new ArrayList<>();

        for (Col col : dataFrame.getCols()) {
            if (col.getObjects().get(0) instanceof DoubleValue || col.getObjects().get(0) instanceof FloatValue || col.getObjects().get(0) instanceof IntegerValue) {
                Double max = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> new Double(i.toString())).max(Comparator.comparing(Double::valueOf)).get();
                Col colToAdd = new Col(col.getName(), "DoubleValue");
                colToAdd.add(new DoubleValue(max));
                newCols.add(colToAdd);
            } else if (col.getObjects().get(0) instanceof BooleanValue) {
                Boolean max = (Boolean) col.getObjects().stream().map(i -> ((Value) i).getInstance()).filter(i -> i.equals(true)).findFirst().orElse(false);
                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(new BooleanValue(max));
                newCols.add(colToAdd);
            } else if (col.getObjects().get(0) instanceof StringValue) {
                Integer max = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> i.toString().length()).max(Comparator.comparing(Integer::valueOf)).get();
                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(new StringValue(max.toString()));
                newCols.add(colToAdd);
            } else {
                DateTime max = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> (DateTime) i).max(Comparator.comparing(DateTime::toDate)).get();
                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(new DateTimeValue(max));
                newCols.add(colToAdd);
            }
        }

        return new DataFrame(newCols);
    }
}
