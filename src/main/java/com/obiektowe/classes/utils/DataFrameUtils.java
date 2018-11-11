package com.obiektowe.classes.utils;

import com.obiektowe.classes.Col;
import com.obiektowe.classes.DataFrame;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class DataFrameUtils {

    public static void insert(DataFrame dataFrame, List<Pair<String, Object>> objects) throws WrongInsertionTypeException {

        boolean inserted = false;
        for (Col col : dataFrame.getCols()) {
            for (Pair pair : objects) {
                if (col.getName().equals(pair.getKey())) {
                    inserted = col.add(pair.getValue());
                }
            }
        }

        if (!inserted) {
            System.out.println("Insertion error");
        }

    }

    public static void dropDatabase(DataFrame dataFrame, int... limit) {

        List<Col> cols = dataFrame.getCols();

        for (Col col : cols) {
            System.out.print(col.getName() + " | ");
        }

        int soutLimit;

        if (limit.length != 0 && cols.get(0).getObjects().size() >= limit[0]) {
            soutLimit = limit[0];
        } else {
            soutLimit = cols.get(0).getObjects().size();
        }

        System.out.println();

        for (int i = 0; i < soutLimit; i++) {

            for (Col col : cols) {
                System.out.print(col.getObjects().get(i) + " | ");
            }
            System.out.println();

        }
    }

}
