package com.obiektowe.classes.Applyable;

import com.obiektowe.classes.Col;
import com.obiektowe.classes.DataFrame;
import com.obiektowe.classes.Interfaces.Applyable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Maximum implements Applyable {
    @Override
    public DataFrame apply(DataFrame dataFrame) {

        List<Col> newCols = new ArrayList<>();

        for (Col col : dataFrame.getCols()) {
            int max = col.getObjects().stream().map(i -> (Integer)i).max(Comparator.comparing(Integer::valueOf)).get();
            Col colToAdd = new Col(col.getName(), col.getType());
            colToAdd.add(max);
            newCols.add(colToAdd);
        }

        return new DataFrame(newCols);
    }
}
