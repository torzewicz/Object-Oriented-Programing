package com.obiektowe;

import com.obiektowe.classes.Applyable.Maximum;
import com.obiektowe.classes.Col;
import com.obiektowe.classes.DataFrame;
import com.obiektowe.classes.Exceptions.NotEqualListsSizeException;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;
import com.obiektowe.classes.GroupedDF;
import com.obiektowe.classes.SparseDataFrame;
import com.obiektowe.classes.Value.IntegerValue;
import com.obiektowe.classes.Value.StringValue;
import com.obiektowe.classes.utils.DataFrameUtils;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.obiektowe.classes.utils.DataFrameUtils.dropDatabase;
import static com.obiektowe.classes.utils.DataFrameUtils.insert;
import static javafx.application.Application.launch;

public class Main {

    public static void main(String[] args) {

        String[] names = {"Imie", "Nazwisko", "Wiek"};
        String[] types = {"StringValue", "StringValue", "IntegerValue"};

        DataFrame dataFrame = null;

        try {
            dataFrame = new DataFrame(names, types);
        } catch (Exception e) {
            e.printStackTrace();
        }


        List<Pair<String, Object>> objects = new ArrayList<>();
        objects.addAll(Arrays.asList(new ImmutablePair<>("Imie", new StringValue("Pawel")), new ImmutablePair<>("Nazwisko", new StringValue("Kowalski")), new ImmutablePair<>("Wiek", new IntegerValue(17))));
        objects.addAll(Arrays.asList(new ImmutablePair<>("Imie", new StringValue("Marek")), new ImmutablePair<>("Nazwisko", new StringValue("Nowak")), new ImmutablePair<>("Wiek", new IntegerValue(43))));
        objects.addAll(Arrays.asList(new ImmutablePair<>("Imie", new StringValue("Dominik")), new ImmutablePair<>("Nazwisko", new StringValue("Smith")), new ImmutablePair<>("Wiek", new IntegerValue(26))));
        objects.addAll(Arrays.asList(new ImmutablePair<>("Imie", new StringValue("Pawel")), new ImmutablePair<>("Nazwisko", new StringValue("Abc")), new ImmutablePair<>("Wiek", new IntegerValue(65))));

        try {
            insert(dataFrame, objects);
        } catch (WrongInsertionTypeException e) {
            e.printStackTrace();
        }

        try {
            DataFrame dataFrame1 = dataFrame.groupby("Imie").apply(new Maximum());
            dropDatabase(dataFrame1);
        } catch (NotEqualListsSizeException e) {
            e.printStackTrace();
        } catch (WrongInsertionTypeException e) {
            e.printStackTrace();
        }

    }
}