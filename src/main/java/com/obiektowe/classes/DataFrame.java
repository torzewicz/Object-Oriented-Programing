package com.obiektowe.classes;


import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DataFrame {

    protected List<Col> cols;

    public DataFrame(String[] colsNames, String[] colsTypes) throws Exception {
        if (colsNames.length != colsTypes.length) {
            throw new Exception("Not equal lists size");
        }

        this.cols = new ArrayList<>();

        for (int i = 0; i < colsNames.length; i++) {
            this.cols.add((new Col(colsNames[i], colsTypes[i])));
        }
    }

    protected DataFrame(List cols) {
        this.cols = cols;
    }

    protected DataFrame(Col col) {
        cols.add(col);
    }

    public DataFrame(File file, String[] colsTypes, boolean header, String... colNames) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        String strLine;
        String[] names = null;
        String[] data = null;
        int i;
        boolean initialized = false;


        if (!header) {
            if (colNames.length != colsTypes.length) {
                throw new Exception("Not equal lists size");
            }

            names = colNames;
        } else {
            while ((strLine = bufferedReader.readLine()) != null) {

                i = 0;

                for (char ch : strLine.toCharArray())
                    if (!(ch == ',')) {
                        data[i] += ch;
                    } else {
                        i++;
                    }

                if (header) {
                    names = data;
                    header = false;
                }

                if (!initialized) {
                    for (int a = 0; a == i; a++) {
                        this.cols.add((new Col(names[i], colsTypes[i])));
                    }
                    initialized = true;
                    continue;
                }

                for (int j = 0; j < data.length; j++) {
                    this.cols.get(j).add(data[j]);
                }

                System.out.println(strLine);
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


    public void dropDatabase() {

        for (Col col : this.cols) {
            System.out.print(col.getName() + ": ");

            for (Object object : col.getObjects()) {
                System.out.print(object + ", ");
            }

            System.out.println("\n");
        }

    }

    public void insert(List<Pair<String, Object>> objects) {

        if (objects.size() != this.size()) {
            System.out.println("Insertion error");
        } else {

            boolean inserted = false;

            for (Col col : this.cols) {

                for (Pair pair : objects) {

                    if (col.getName().equals(pair.getKey())) {
                        inserted = col.add(pair.getValue());
                        if (inserted) {
                            System.out.println("Inserted " + pair.getValue() + " to " + pair.getKey());
                        }
                    }
                }
            }

            if (!inserted) {
                System.out.println("Insertion error");
            }

        }
    }

}
