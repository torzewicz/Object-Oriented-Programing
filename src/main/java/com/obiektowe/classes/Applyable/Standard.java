package com.obiektowe.classes.Applyable;

import com.obiektowe.classes.Col;
import com.obiektowe.classes.DataFrame;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;
import com.obiektowe.classes.Interfaces.Applyable;
import com.obiektowe.classes.Value.*;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

public class Standard implements Applyable {
    @Override
    public DataFrame apply(DataFrame dataFrame) throws WrongInsertionTypeException {

        Variance variance = new Variance();
        DataFrame varianceDataFrame = variance.apply(dataFrame);
        List<Col> newCols = new ArrayList<>();

        ThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(dataFrame.size());
        LinkedList<Future<?>> futures = new LinkedList<>();

        for (Col col : varianceDataFrame.getCols()) {

            futures.add(threadPoolExecutor.submit(() -> {

                if (col.getObjects().get(0) instanceof DoubleValue || col.getObjects().get(0) instanceof FloatValue || col.getObjects().get(0) instanceof IntegerValue) {
                Double standard = Math.sqrt(new Double(col.getObjects().get(0).toString()));

                Col colToAdd = new Col(col.getName(), "DoubleValue");
                    try {
                        colToAdd.add(new DoubleValue(standard));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);
            } else if (col.getObjects().get(0) instanceof BooleanValue) {
                Boolean standard = Boolean.valueOf(col.getObjects().get(0).toString());
                Col colToAdd = new Col(col.getName(), col.getType());
                    try {
                        colToAdd.add(new BooleanValue(standard));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);

            } else if (col.getObjects().get(0) instanceof StringValue) {
                Double standard = Math.sqrt(new Double(col.getObjects().get(0).toString()));
                Col colToAdd = new Col(col.getName(), col.getType());
                    try {
                        colToAdd.add(new StringValue(standard.toString()));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);
            } else {
                Double standard = Math.sqrt((double) ((DateTime) col.getObjects().get(0)).getMillis());
                Col colToAdd = new Col(col.getName(), col.getType());
                    try {
                        colToAdd.add(new DateTimeValue(new DateTime(standard)));
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
