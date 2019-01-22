package com.obiektowe.classes.Applyable;

import com.obiektowe.classes.Col;
import com.obiektowe.classes.DataFrame;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;
import com.obiektowe.classes.Interfaces.Applyable;
import com.obiektowe.classes.Value.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class Variance implements Applyable {
    @Override
    public DataFrame apply(DataFrame dataFrame) throws WrongInsertionTypeException {

        Mean mean = new Mean();
        DataFrame meanDataFrame = mean.apply(dataFrame);
        List<Col> newCols = new ArrayList<>();

        ThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(dataFrame.size());
        LinkedList<Future<?>> futures = new LinkedList<>();

        for (Col col : dataFrame.getCols()) {

            futures.add(threadPoolExecutor.submit(() -> {

                if (col.getObjects().get(0) instanceof DoubleValue || col.getObjects().get(0) instanceof FloatValue || col.getObjects().get(0) instanceof IntegerValue) {
                    Double average = new Double(meanDataFrame.get(col.getName()).getObjects().get(0).toString());

                    Double nominator = col.getObjects().stream().map(i -> ((Value) i).getInstance()).mapToDouble(i -> new Double(i.toString())).map(i -> Math.pow(i - average, 2)).sum();

                    Double variance = Math.sqrt(nominator / col.getObjects().size());

                    Col colToAdd = new Col(col.getName(), "DoubleValue");
                    try {
                        colToAdd.add(new DoubleValue(variance));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);

                } else if (col.getObjects().get(0) instanceof BooleanValue) {
                    Boolean more = Boolean.valueOf(meanDataFrame.get(col.getName()).getObjects().get(0).toString());
                    Col colToAdd = new Col(col.getName(), col.getType());
                    try {
                        colToAdd.add(new BooleanValue(more));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);
                } else if (col.getObjects().get(0) instanceof StringValue) {
                    Double average = new Double(meanDataFrame.get(col.getName()).getObjects().get(0).toString());
                    Double nominator = col.getObjects().stream().map(i -> ((Value) i).getInstance()).mapToDouble(i -> new Double(i.toString())).map(i -> Math.pow(i - average, 2)).sum();
                    Double variance = Math.sqrt(nominator / col.getObjects().size());

                    Col colToAdd = new Col(col.getName(), col.getType());
                    try {
                        colToAdd.add(new StringValue(variance.toString()));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);

                } else {
                    Double average = new Double(meanDataFrame.get(col.getName()).getObjects().get(0).toString());
                    Double nominator = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> (DateTime) i).mapToDouble(DateTime::getMillis).map(i -> Math.pow(i - average, 2)).sum();
                    Double variance = Math.sqrt(nominator / col.getObjects().size());

                    Col colToAdd = new Col(col.getName(), col.getType());
                    try {
                        colToAdd.add(new DateTimeValue(new DateTime(variance)));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);
                }
            }));
        }


        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return new DataFrame(newCols);
    }
}
