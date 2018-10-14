package com.obiektowe;

import com.obiektowe.classes.DataFrame;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        String[] names = {"Imie", "Nazwisko", "Wiek"};
        String[] types = {"String", "String", "Integer"};

        DataFrame dataFrame = null;

        try {
            dataFrame = new DataFrame(names, types);
        } catch (Exception e) {
            e.printStackTrace();
        }



        List<Pair<String, Object>> objects = new ArrayList<>();
        objects.addAll(Arrays.asList(new ImmutablePair<>("Imie", "Pawel"), new ImmutablePair<>("Nazwisko","Kowalski"), new ImmutablePair<>("Wiek",17)));

        dataFrame.dropDatabase();

        dataFrame.insert(objects);

        dataFrame.dropDatabase();

    }

}
