package com.obiektowe.classes;

import com.obiektowe.classes.DataFrame;
import com.obiektowe.classes.Interfaces.Groupby;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class GroupedDF implements Groupby {

    private List<DataFrame> dataFrames;


    // not sure if this even works - not time to make sure
    public GroupedDF(String[] colNames, DataFrame dataFrame) throws Exception {
        dataFrames = new LinkedList<>();
        List<Col> allCols = dataFrame.cols;
        List<Col> sortByCols = new ArrayList<>();

        for (String s : colNames) {
            sortByCols.add(dataFrame.get(s)); // Group by these cols
        }

        for (Col sortByCol : sortByCols) {
            List<Object> groupByObjects = sortByCol.getObjects().stream().distinct().collect(Collectors.toList()); // Then group by these objects
            int numberOfDF = (int)sortByCol.getObjects().stream().distinct().count(); // How many DF there will be

            for (int i = 0; i < numberOfDF; i++) {
                dataFrames.add(new DataFrame(allCols.stream().map(a -> a.getName()).collect(Collectors.toList()).toArray(new String[allCols.size()]), allCols.stream().map(a -> a.getType()).collect(Collectors.toList()).toArray(new String[allCols.size()])));
            } // So create so many DF's

            List<Pair<Object, Integer>> listOfObjectsAndItsIndex = new ArrayList<>(); // Want to know which index relates to wanted object

            sortByCol.getObjects().stream().forEach(i -> listOfObjectsAndItsIndex.add(new ImmutablePair<>(i, sortByCol.getObjects().indexOf(i)))); // Add index and its object to list

            for (Object object : groupByObjects) {

                List<Pair<String,Object>> insertionObjects= new ArrayList<>();
                List<Integer> indexes = new ArrayList<>();

                for (Pair pair : listOfObjectsAndItsIndex) {

                    if (pair.getKey().equals(object)) {
                        insertionObjects.add(new ImmutablePair<>(sortByCol.getName(), object));
                        indexes.add((Integer) pair.getValue());
                    }

                }

                dataFrames.get(groupByObjects.indexOf(object)).insert(insertionObjects);

                for (Col col : dataFrames.get(groupByObjects.indexOf(object)).cols) {

                    if (!col.getName().equals(sortByCol.getName())) {

                        int wantedIndex = listOfObjectsAndItsIndex.stream().filter(i -> i.getKey().equals(object)).map(i -> i.getValue()).findFirst().get();
                        col.add(dataFrame.get(col.getName()).getObjects().get(wantedIndex));

                    }

                }

            }

        }
    }

}
