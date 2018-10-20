package com.obiektowe.classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SparseDataFrame extends DataFrame {

    private List<Col> cols;

    private String valueToHide;

    public SparseDataFrame(String[] colsNames, String[] colsTypes, String valueToHide) throws Exception {
        super(colsNames, colsTypes);
        this.valueToHide = valueToHide;
    }

    public SparseDataFrame(DataFrame dataFrame, String valueToHide) {
        super(dataFrame.cols);
        this.cols = dataFrame.cols;
        for (Col col : this.cols) {
            ArrayList<Object> temp = null;
            int index = 0;
            for (Object object : col.getObjects()) {
                if (!(object.equals(valueToHide))) {
                    temp.add(new C00Value(index, object));
                }
                index++;
            }
            col.setObjects(temp);
        }

    }


    public SparseDataFrame(File file, String[] colsTypes, boolean header, String... colNames) throws Exception {
        super(file, colsTypes, header, colNames);
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


    public boolean addValue(String colName, Object object) {
        return true;
    }

    public static DataFrame toDense(SparseDataFrame sparseDataFrame) throws Exception {
        List<Col> cols = sparseDataFrame.cols;
        List<String> colNames = new ArrayList<String>();
        List<String> colTypes = new ArrayList<String>();

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


}
