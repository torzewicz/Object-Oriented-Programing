package com.obiektowe.classes

public class SparseDataFrame extends DataFrame {

    private List<Col> cols;

    private String valueToHide;

    public SparseDataFrame(String[] colsNames, String[] colsTypes, String valueToHide) {
        super(colsNames, colsTypes);
        this.valueToHide = valueToHide;
    }



    public SparseDataFrame(DataFrame dataFrame, String valueToHide) {
        this.cols = dataFrame.cols;
        for(Col col : this.cols) {
            private List<C00Value> temp = new ArrayList();
            int index = 0;
            for(Object object : col.objects) {
                if(!((String)object.equals(valueToHide)) {
                    temp.add(new C00Value(index, object));
                }
                index++;
            }
            col.setObjects(temp;)
        }

    }


    public boolean addValue(String colName, Object object) {

    }

    public static DataFrame toDense(SparseDataFrame sparseDataFrame) {
        DataFrame dataFrame = new DataFrame(this.cols);

        for (Col col : dataFrame.cols) {
            private List<Object> temp = new ArrayList();
            for(C00Value c00value : col.objects) {
                
            }
        }

    }




}
