package com.obiektowe.classes.Applyable;

import com.obiektowe.classes.Col;
import com.obiektowe.classes.DataFrame;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;
import com.obiektowe.classes.Interfaces.Applyable;
import com.obiektowe.classes.Value.*;
import org.joda.time.DateTime;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class Maximum implements Applyable {
    @Override
    public DataFrame apply(DataFrame dataFrame) {

        List<Col> newCols = new ArrayList<>();

        ThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(dataFrame.size());
        LinkedList<Future<?>> futures = new LinkedList<>();

        for (Col col : dataFrame.getCols()) {

            futures.add(threadPoolExecutor.submit(() -> {

                if (col.getObjects().get(0) instanceof DoubleValue || col.getObjects().get(0) instanceof FloatValue || col.getObjects().get(0) instanceof IntegerValue) {
                    Double max = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> new Double(i.toString())).max(Comparator.comparing(Double::valueOf)).get();
                    Col colToAdd = new Col(col.getName(), "DoubleValue");
                    try {
                        colToAdd.add(new DoubleValue(max));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);
                } else if (col.getObjects().get(0) instanceof BooleanValue) {
                    Boolean max = (Boolean) col.getObjects().stream().map(i -> ((Value) i).getInstance()).filter(i -> i.equals(true)).findFirst().orElse(false);
                    Col colToAdd = new Col(col.getName(), col.getType());
                    try {
                        colToAdd.add(new BooleanValue(max));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);
                } else if (col.getObjects().get(0) instanceof StringValue) {
                    Integer max = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> i.toString().length()).max(Comparator.comparing(Integer::valueOf)).get();
                    Col colToAdd = new Col(col.getName(), col.getType());
                    try {
                        colToAdd.add(new StringValue(max.toString()));
                    } catch (WrongInsertionTypeException e) {
                        e.printStackTrace();
                    }
                    newCols.add(colToAdd);
                } else {
                    DateTime max = col.getObjects().stream().map(i -> ((Value) i).getInstance()).map(i -> (DateTime) i).max(Comparator.comparing(DateTime::toDate)).get();
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
                System.out.println("12");
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return new DataFrame(newCols);
    }

    private static Object[][] chunkArray(Object[] array, int chunkSize) {
        int numOfChunks = (int) Math.ceil((double) array.length / chunkSize);
        Object[][] output = new Object[numOfChunks][];

        for (int i = 0; i < numOfChunks; ++i) {
            int start = i * chunkSize;
            int length = Math.min(array.length - start, chunkSize);

            Object[] temp = new Object[length];
            System.arraycopy(array, start, temp, 0, length);
            output[i] = temp;
        }

        return output;
    }
}

