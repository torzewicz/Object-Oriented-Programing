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

public class Standard implements Applyable {
    @Override
    public DataFrame apply(DataFrame dataFrame) throws WrongInsertionTypeException {

        Variance variance = new Variance();
        DataFrame varianceDataFrame = variance.apply(dataFrame);
        List<Col> newCols = new ArrayList<>();

        for (Col col : varianceDataFrame.getCols()) {

            if (col.getObjects().get(0) instanceof DoubleValue || col.getObjects().get(0) instanceof FloatValue || col.getObjects().get(0) instanceof IntegerValue) {
                Double standard = Math.sqrt(new Double(col.getObjects().get(0).toString()));

                Col colToAdd = new Col(col.getName(), "DoubleValue");
                colToAdd.add(new DoubleValue(standard));
                newCols.add(colToAdd);
            } else if (col.getObjects().get(0) instanceof BooleanValue) {
                Boolean standard = Boolean.valueOf(col.getObjects().get(0).toString());
                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(new BooleanValue(standard));
                newCols.add(colToAdd);

            } else if (col.getObjects().get(0) instanceof StringValue) {
                Double standard = Math.sqrt(new Double(col.getObjects().get(0).toString()));

                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(new StringValue(variance.toString()));
                newCols.add(colToAdd);
            } else {
                Double standard = Math.sqrt((double) ((DateTime) col.getObjects().get(0)).getMillis());
                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(new DateTimeValue(new DateTime(standard)));
                newCols.add(colToAdd);
            }
        }

        return new DataFrame(newCols);

    }
}
