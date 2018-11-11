package com.obiektowe.classes;

import com.obiektowe.classes.Exceptions.NotEqualListsSizeException;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;
import com.obiektowe.classes.Value.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataFrame {

    private List<Col> cols;

    public List<Col> getCols() {
        return cols;
    }

    public DataFrame(String[] colsNames, String[] colsTypes) throws NotEqualListsSizeException {
        if (colsNames.length != colsTypes.length) {
            throw new NotEqualListsSizeException("Not equal lists size");
        }

        this.cols = new ArrayList<>();

        for (int i = 0; i < colsNames.length; i++) {
            this.cols.add((new Col(colsNames[i], colsTypes[i])));
        }
    }

    public DataFrame(List cols) {
        this.cols = cols;
    }

    protected DataFrame(Col col) {
        cols.add(col);
    }

    public DataFrame(String pathToFile, String[] colsTypes, boolean header, String... colNames) throws IOException, NotEqualListsSizeException, WrongInsertionTypeException, IllegalAccessException, InstantiationException {
        FileInputStream fileInputStream = new FileInputStream(new File(pathToFile));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        String strLine;
        String[] names;
        String[] dataString;
        boolean inserted = false;

        this.cols = new ArrayList<>(colsTypes.length);

        if (!header) {
            if (colNames.length != colsTypes.length) {
                throw new NotEqualListsSizeException("Not equal lists size");
            }
            names = colNames;
        } else {
            names = bufferedReader.readLine().split(",");
        }

        for (int a = 0; a < colsTypes.length; a++) {
            this.cols.add(new Col(names[a], colsTypes[a]));
        }

//        bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        while ((strLine = bufferedReader.readLine()) != null) {

            dataString = strLine.split(",");

            List<Pair<String, Object>> objects = new ArrayList<>();

            for (int i = 0; i < dataString.length; i++) {
                objects.add(new ImmutablePair<>(names[i], dataString[i]));
            }

            // Similar to insert method
            for (Col col : this.cols) {

                String stringInstance = "com.obiektowe.classes.Value.";
                Class instance = null;

                try {
                    stringInstance += col.getType();
                    instance = Class.forName(stringInstance);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                for (Pair pair : objects) {
                    Object objectToInsert = ((Value) instance.newInstance()).create(pair.getValue().toString());
                    if (col.getName().equals(pair.getKey())) {
                        inserted = col.add(objectToInsert);
                    }
                }
            }

            if (!inserted) {
                System.out.println("Insertion error");
            }

        }


    }

    public int size() {
        return this.cols.size();
    }

    public Col get(String colName) {

        for (Col col : this.cols) {
            if (col.getName().equals(colName)) {
                return col;
            }
        }

        return null;
    }

    public DataFrame get(String[] cols, boolean copy) {

        List<Col> tempCols = new ArrayList<>();

        for (String colName : cols) {
            for (Col col : this.cols) {
                if (colName.equals(col.getName())) {
                    if (copy) {
                        tempCols.add(new Col(col.getName(), col.getType()));
                    } else tempCols.add(col);
                }
            }
        }

        if (tempCols.size() != 0) {
            return new DataFrame(tempCols);
        } else return null;
    }

    public DataFrame iloc(int i) {
        return new DataFrame(this.cols.get(i));
    }


    public DataFrame iloc(int from, int to) {
        return new DataFrame(this.cols.subList(from, to));
    }


    public DataFrame getDataFrame() {
        return this;
    }
}
