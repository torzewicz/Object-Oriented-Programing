package com.obiektowe.classes.Applyable;

import com.obiektowe.classes.Col;
import com.obiektowe.classes.DataFrame;
import com.obiektowe.classes.Interfaces.Applyable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Mean implements Applyable {
    @Override
    public DataFrame apply(DataFrame dataFrame) {
        {

            List<Col> newCols = new ArrayList<>();

            for (Col col : dataFrame.getCols()) {
                Double mean = col.getObjects().stream().mapToInt(i -> new Integer(i.toString())).average().getAsDouble();
                Col colToAdd = new Col(col.getName(), col.getType());
                colToAdd.add(mean);
                newCols.add(colToAdd);
            }

            return new DataFrame(newCols);
        }
    }
}
