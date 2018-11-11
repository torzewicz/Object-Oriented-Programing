package com.obiektowe.classes;

import com.obiektowe.classes.Exceptions.NotEqualListsSizeException;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;
import com.obiektowe.classes.Interfaces.Applyable;
import com.obiektowe.classes.Interfaces.Groupby;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.stream.Collectors;

public class GroupedDF implements Groupby {

    private List<DataFrame> dataFrames;

    public GroupedDF(String[] colNames, DataFrame dataFrame) throws NotEqualListsSizeException, WrongInsertionTypeException {
        dataFrames = new LinkedList<>();
        List<Col> allCols = dataFrame.getCols();
        List<Col> sortByCols = new ArrayList<>();

        for (String s : colNames) {
            sortByCols.add(dataFrame.get(s)); // Group by these cols
        }

        for (Col sortByCol : sortByCols) {

            List<Object> groupByObjects = new ArrayList<>();

            for (Object object : sortByCol.getObjects()) { //group by these objects
                boolean toInsert = true;
                for (Object o : groupByObjects) {
                    if (o.toString().equals(object.toString())) {
                        toInsert = false;
                    }
                }
                if (toInsert) {
                    groupByObjects.add(object);
                }

            }

            System.out.println(groupByObjects);

            int numberOfDF = (int) sortByCol.getObjects().stream().map(i -> i.toString()).distinct().count(); // How many DF there will be

            for (int i = 0; i < numberOfDF; i++) { //wow
                dataFrames.add(new DataFrame(allCols.stream().map(a -> a.getName()).collect(Collectors.toList()).toArray(new String[allCols.size()]), allCols.stream().map(a -> a.getType()).collect(Collectors.toList()).toArray(new String[allCols.size()])));
            } // create so many DF's

            List<Pair<Object, Integer>> listOfObjectsAndItsIndex = new ArrayList<>(); // Want to know which index relates to wanted object

            for (int i = 0; i < sortByCol.getObjects().size(); i++) {
                listOfObjectsAndItsIndex.add(new ImmutablePair<>(sortByCol.getObjects().get(i), i)); // Add index and its object to list
            }

            for (Object object : groupByObjects) {

                int howManyObjects = (int) dataFrame.get(sortByCol.getName()).getObjects().stream().filter(i -> i.toString().equals(object.toString())).count();

                List<Integer> indexes = new ArrayList<>();
                List<Pair<String, Object>> insertionObjects = new ArrayList<>();

                for (Pair pair : listOfObjectsAndItsIndex) {

                    if (pair.getKey().toString().equals(object.toString())) {
                        insertionObjects.add(new ImmutablePair<>(sortByCol.getName(), object));
                        indexes.add((Integer) pair.getValue());
                    }

                }

                System.out.println(insertionObjects + " " + indexes);

                boolean inserted = false;

                for (Col col : dataFrames.get(groupByObjects.stream().map(Object::toString).collect(Collectors.toList()).indexOf(object.toString())).getCols()) {
                    for (Pair pair : insertionObjects) {
                        if (col.getName().equals(pair.getKey())) {
                            inserted = col.add(pair.getValue());
                        }
                    }
                }

                if (!inserted) {
                    System.out.println("Insertion error");
                }


                for (Col col : dataFrames.get(groupByObjects.stream().map(Object::toString).collect(Collectors.toList()).indexOf(object.toString())).getCols()) {

                    if (!col.getName().equals(sortByCol.getName())) {

                        List<Integer> wantedIndex = listOfObjectsAndItsIndex.stream().filter(i -> i.getKey().toString().equals(object.toString())).map(Pair::getValue).collect(Collectors.toList());
                        for (Integer integer : wantedIndex) {
                            col.add(dataFrame.get(col.getName()).getObjects().get(integer));
                        }

                    }

                }
            }

        }
    }

    @Override
    public DataFrame apply(Applyable applyable) {
        return null;
    }

    @Override
    public DataFrame max() {
        return null;
    }

    @Override
    public DataFrame min() {
        return null;
    }

    @Override
    public DataFrame mean() {
        return null;
    }

    @Override
    public DataFrame std() {
        return null;
    }

    @Override
    public DataFrame sum() {
        return null;
    }

    @Override
    public DataFrame var() {
        return null;
    }

    public List<DataFrame> getDataFrames() {
        return dataFrames;
    }

    public void setDataFrames(List<DataFrame> dataFrames) {
        this.dataFrames = dataFrames;
    }

}
