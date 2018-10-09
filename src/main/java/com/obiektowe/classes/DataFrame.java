package com.obiektowe.classes;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataFrame {

    protected List<Col> cols;

    public DataFrame(String[] colsNames, String[] colsTypes) throws Exception {
        if (colsNames.length != colsTypes.length) {
            throw new Exception("Not equal lists size");
        }

        for (int i = 0; i < colsNames.length; i++) {
            this.cols.add(new Col(colsNames[i], colsTypes[i]));
        }

    }

    private DataFrame(List cols) {
        this.cols = cols;
    }

    private DataFrame(Col col) {
        cols.add(col);
    }

    public int size() {
        return this.cols.size();
    }

    public Col get(String colname) {

        for (Col col : this.cols) {
            if (col.getName().equals(colname)) {
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

}
