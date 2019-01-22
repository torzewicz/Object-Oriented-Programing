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

public class Minimum implements Applyable {
    @Override
    public DataFrame apply(DataFrame dataFrame) {

        List<Col> newCols = new ArrayList<>();

        ThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(dataFrame.size());
        LinkedList<Future<?>> futures = new LinkedList<>();


        for (Col col : dataFrame.getCols()) {

            futures.add(threadPoolExecutor.submit(() -> {

                if (col.getObjects().get(0) instanceof DoubleValue || col.getObjects().get(0) instanceof FloatValue || col.getObjects().get(0) instanceof IntegerValue) {
                Double min = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> new Double(i.toString())).min(Comparator.comparing(Double::valueOf)).get();
                Col colToAdd = new Col(col.getName(), "DoubleValue");
                    try {
                        colToAdd.add(new DoubleValue(min));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);
            } else if (col.getObjects().get(0) instanceof BooleanValue) {
                Boolean min = (Boolean) col.getObjects().stream().map(i -> ((Value) i).getInstance()).filter(i -> i.equals(true)).findFirst().orElse(false);
                Col colToAdd = new Col(col.getName(), col.getType());
                    try {
                        colToAdd.add(new BooleanValue(min));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);
            } else if (col.getObjects().get(0) instanceof StringValue) {
                Integer min = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> i.toString().length()).min(Comparator.comparing(Integer::valueOf)).get();
                Col colToAdd = new Col(col.getName(), col.getType());
                    try {
                        colToAdd.add(new StringValue(min.toString()));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);
            } else {
                DateTime max = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> (DateTime) i).min(Comparator.comparing(DateTime::toDate)).get();
                Col colToAdd = new Col(col.getName(), col.getType());
                    try {
                        colToAdd.add(new DateTimeValue(max));
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
