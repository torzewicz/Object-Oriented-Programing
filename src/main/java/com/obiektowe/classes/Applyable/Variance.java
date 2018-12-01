package com.obiektowe.classes.Applyable;

import com.obiektowe.classes.Col;
import com.obiektowe.classes.DataFrame;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;
import com.obiektowe.classes.Interfaces.Applyable;
import com.obiektowe.classes.Value.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class Variance implements Applyable {
    @Override
    public DataFrame apply(DataFrame dataFrame) throws WrongInsertionTypeException {

        Mean mean = new Mean();
        DataFrame meanDataFrame = mean.apply(dataFrame);
        List<Col> newCols = new ArrayList<>();

        for (Col col : dataFrame.getCols()) {

            if (col.getObjects().get(0) instanceof DoubleValue || col.getObjects().get(0) instanceof FloatValue || col.getObjects().get(0) instanceof IntegerValue) {
                Double average = new Double(meanDataFrame.get(col.getName()).getObjects().get(0).toString());

                Double nominator = col.getObjects().stream().map(i -> ((Value) i).getInstance()).mapToDouble(i -> new Double(i.toString())).map(i -> Math.pow(i - average, 2)).sum();

                Double variance = Math.sqrt(nominator/col.getObjects().size());

                Col colToAdd = new Col(col.getName(), "DoubleValue");
                colToAdd.add(new DoubleValue(variance));
                newCols.add(colToAdd);

            } else if (col.getObjects().get(0) instanceof BooleanValue) {
                Boolean more = Boolean.valueOf(meanDataFrame.get(col.getName()).getObjects().get(0).toString());
                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(new BooleanValue(more));
                newCols.add(colToAdd);
            } else if (col.getObjects().get(0) instanceof StringValue) {
                Double average = new Double(meanDataFrame.get(col.getName()).getObjects().get(0).toString());
                Double nominator = col.getObjects().stream().map(i -> ((Value) i).getInstance()).mapToDouble(i -> new Double(i.toString())).map(i -> Math.pow(i - average, 2)).sum();
                Double variance = Math.sqrt(nominator/col.getObjects().size());

                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(new StringValue(variance.toString()));
                newCols.add(colToAdd);

            } else {
                Double average = new Double(meanDataFrame.get(col.getName()).getObjects().get(0).toString());
                Double nominator = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> (DateTime) i).mapToDouble(DateTime::getMillis).map(i -> Math.pow(i - average, 2)).sum();
                Double variance = Math.sqrt(nominator/col.getObjects().size());

                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(new DateTimeValue(new DateTime(variance)));
                newCols.add(colToAdd);
            }
        }

        return new DataFrame(newCols);
    }
}
