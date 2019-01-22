package com.obiektowe.classes;

import com.obiektowe.classes.Exceptions.NotEqualListsSizeException;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;
import com.obiektowe.classes.Value.*;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.awaitility.Awaitility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

public class DataFrame {

    private List<Col> cols;

    public List<Col> getCols() {
        return cols;
    }

    public DataFrame() {}

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
        this.cols = new ArrayList<>(cols);
    }

    protected DataFrame(Col col) {
        cols.add(col);
    }

    public DataFrame(String pathToFile, String[] colsTypes, boolean header, String... colNames) throws IOException, NotEqualListsSizeException {
        FileInputStream fileInputStream = new FileInputStream(new File(pathToFile));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        String strLine;
        String[] names;
        String[] dataString = new String[colsTypes.length];

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
        int line = 0;
        List<Pair<String, Object>> objects = new ArrayList<>();

        while ((strLine = bufferedReader.readLine()) != null) {
            dataString = strLine.split(",");

            for (int i = 0; i < dataString.length; i++) {
                objects.add(new ImmutablePair<>(names[i], dataString[i]));
            }

            line++;
        }

        for (Col col : this.cols) {
            new DataFrameThread(col, objects).start();
        }

        int finalLine = line;
        String[] finalDataString = dataString;
        Awaitility.await().atMost(20, TimeUnit.SECONDS).pollInterval(200, TimeUnit.MILLISECONDS).ignoreExceptions().until(() -> {
            boolean statement = true;
            for (int i = 0; i < finalDataString.length; i++) {
                if (this.getCols().get(i).getObjects().size() != finalLine) {
                    statement = false;
                }
            }
            return statement;
        });

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

    public GroupedDF groupBy(String... cols) throws NotEqualListsSizeException, WrongInsertionTypeException {
        return new GroupedDF(cols, this);
    }

    public DataFrame getDataFrame() {
        return this;
    }

}

class DataFrameThread extends Thread {

    private Col col;
    private List<Pair<String, Object>> objects;

    DataFrameThread(Col col, List<Pair<String, Object>> objects) {
        this.col = col;
        this.objects = objects;
    }

    public void run() {
        Class instance = null;
        String stringInstance = "com.obiektowe.classes.Value.";

        try {
            stringInstance += this.col.getType();
            instance = Class.forName(stringInstance);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Pair pair : this.objects) {
            Object objectToInsert = null;
            try {
                objectToInsert = ((Value) requireNonNull(instance).newInstance()).create(pair.getValue().toString());
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            if (this.col.getName().equals(pair.getKey())) {
                try {
                    this.col.add(objectToInsert);
                } catch (WrongInsertionTypeException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
