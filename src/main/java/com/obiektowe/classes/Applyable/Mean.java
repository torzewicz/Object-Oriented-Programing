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

public class Mean implements Applyable {
    @Override
    public DataFrame apply(DataFrame dataFrame) {

        List<Col> newCols = new ArrayList<>();


        ThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(dataFrame.size());
        LinkedList<Future<?>> futures = new LinkedList<>();

        for (Col col : dataFrame.getCols()) {

            futures.add(threadPoolExecutor.submit(() -> {

                if (col.getObjects().get(0) instanceof DoubleValue || col.getObjects().get(0) instanceof FloatValue || col.getObjects().get(0) instanceof IntegerValue) {

                    Double mean = col.getObjects().stream().map(i -> ((Value) i).getInstance()).mapToDouble(i -> new Double(i.toString())).average().getAsDouble();
                    Col colToAdd = new Col(col.getName(), "DoubleValue");
                    try {
                        colToAdd.add(new DoubleValue(mean));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);
                } else if (col.getObjects().get(0) instanceof BooleanValue) {
                    Long trues = col.getObjects().stream().map(i -> ((Value) i).getInstance()).filter(i -> i.equals(true)).count();
                    Long falses = col.getObjects().stream().map(i -> ((Value) i).getInstance()).filter(i -> i.equals(false)).count();
                    Boolean more = trues >= falses;
                    Col colToAdd = new Col(col.getName(), col.getType());
                    try {
                        colToAdd.add(new BooleanValue(more));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);
                } else if (col.getObjects().get(0) instanceof StringValue) {
                    Double average = col.getObjects().stream().map(i -> ((Value) i).getInstance()).mapToInt(i -> i.toString().length()).average().getAsDouble();
                    Col colToAdd = new Col(col.getName(), col.getType());
                    try {
                        colToAdd.add(new StringValue(average.toString()));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);
                } else {
                    Double average = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> (DateTime) i).mapToLong(DateTime::getMillis).average().getAsDouble();
                    Col colToAdd = new Col(col.getName(), col.getType());
                    try {
                        colToAdd.add(new DateTimeValue(new DateTime(average)));
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
