package com.obiektowe.classes;

import com.obiektowe.classes.Exceptions.NotEqualListsSizeException;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SparseDataFrame extends DataFrame {

    private List<Col> cols;

    private String valueToHide;

    public SparseDataFrame(String[] colsNames, String[] colsTypes, String valueToHide) throws NotEqualListsSizeException {
        super(colsNames, colsTypes);
        this.valueToHide = valueToHide;
    }

    public SparseDataFrame(DataFrame dataFrame, String valueToHide) throws WrongInsertionTypeException {
        super(dataFrame.getCols());
        this.cols = new ArrayList<>();
        boolean inserted = false;

        for (Col col : dataFrame.getCols()) {
            this.cols.add(new Col(col.getName(), "C00Value"));
        }

        List<Pair<String, Object>> whatToAdd = new ArrayList<>();

        for (Col col : dataFrame.getCols()) {
            int index = 0;
            for (Object object : col.getObjects()) {
                if (!(object.equals(valueToHide))) {
                    whatToAdd.add(new ImmutablePair<>(col.getName(), new C00Value(index, object)));
                }
                index++;
            }
        }

        for (Col col : this.cols) {
            for (Pair pair : whatToAdd) {
                if (col.getName().equals(pair.getKey())) {
                    inserted = col.add(pair.getValue());
                }
            }
        }

        if (!inserted) {
            System.out.println("Insertion error");
        }


    }

    public SparseDataFrame(String pathToFile, String[] colsTypes, boolean header, String valueToHide, String... colNames) throws NotEqualListsSizeException, IOException, WrongInsertionTypeException, InstantiationException, IllegalAccessException {
        super(pathToFile, colsTypes, header, colNames);
        boolean inserted = false;

        // to be changed
        DataFrame dataFrame = new DataFrame(pathToFile, colsTypes, header, colNames);

        this.cols = new ArrayList<>();

        for (Col col : dataFrame.getCols()) {
            this.cols.add(new Col(col.getName(), "C00Value"));
        }

        List<Pair<String, Object>> whatToAdd = new ArrayList<>();

        for (Col col : dataFrame.getCols()) {
            int index = 0;
            for (Object object : col.getObjects()) {
                if (!(object.toString().equals(valueToHide))) {
                    whatToAdd.add(new ImmutablePair<>(col.getName(), new C00Value(index, object)));
                }
                index++;
            }
        }

        for (Col col : this.cols) {
            for (Pair pair : whatToAdd) {
                if (col.getName().equals(pair.getKey())) {
                    inserted = col.add(pair.getValue());
                }
            }
        }

        if (!inserted) {
            System.out.println("Insertion error");
        }

    }


    public boolean addValue(String colName, Object object) {
        return true;
    }

    public static DataFrame toDense(SparseDataFrame sparseDataFrame) throws NotEqualListsSizeException {
        List<Col> cols = sparseDataFrame.cols;
        List<String> colNames = new ArrayList<>();
        List<String> colTypes = new ArrayList<>();

        for (Col col : cols) {
            colNames.add(col.getName());
            colTypes.add(col.getType());
        }

        DataFrame dataFrame = new DataFrame(colNames.toArray(new String[colNames.size()]), colTypes.toArray(new String[colTypes.size()]));

        for (Col col : sparseDataFrame.cols) {
            List<Object> temp = new ArrayList();
            for (Object c00value : col.getObjects()) {
                temp.add(((C00Value)c00value).getIndex(),((C00Value)c00value).getValue());
            }
        }

        return null;

    }

    @Override
    public List<Col> getCols() {
        return cols;
    }


}
