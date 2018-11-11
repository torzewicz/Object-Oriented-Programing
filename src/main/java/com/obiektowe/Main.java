package com.obiektowe;

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

public class Main extends Application {

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
        objects.addAll(Arrays.asList(new ImmutablePair<>("Imie", new StringValue("Pawel")), new ImmutablePair<>("Nazwisko",new StringValue("Kowalski")), new ImmutablePair<>("Wiek",new IntegerValue(17))));
        objects.addAll(Arrays.asList(new ImmutablePair<>("Imie", new StringValue("Marek")), new ImmutablePair<>("Nazwisko",new StringValue("Nowak")), new ImmutablePair<>("Wiek",new IntegerValue(43))));
        objects.addAll(Arrays.asList(new ImmutablePair<>("Imie", new StringValue("Dominik")), new ImmutablePair<>("Nazwisko",new StringValue("Smith")), new ImmutablePair<>("Wiek",new IntegerValue(26))));
        objects.addAll(Arrays.asList(new ImmutablePair<>("Imie", new StringValue("Pawel")), new ImmutablePair<>("Nazwisko",new StringValue("Abc")), new ImmutablePair<>("Wiek",new IntegerValue(65))));

//        dataFrame.dropDatabase();

        try {
            insert(dataFrame, objects);
        } catch (WrongInsertionTypeException e) {
            e.printStackTrace();
        }


//        dataFrame.dropDatabase();

        dropDatabase(dataFrame);

        String[] colName = {"Imie"};

        GroupedDF groupedDF = null;


        try {
            groupedDF = new GroupedDF(colName, dataFrame);
        } catch (NotEqualListsSizeException e) {
            e.printStackTrace();
        } catch (WrongInsertionTypeException e) {
            e.printStackTrace();
        }


        for (DataFrame dataFrame1 : groupedDF.getDataFrames()) {
//            dropDatabase(dataFrame1);
//            System.out.println(dataFrame1.getCols());
            for (Col col : dataFrame1.getCols()) {
                System.out.print(col.getName() + " " + col.getType() + " ");
                System.out.println(col.getObjects());
            }

            System.out.println();
        }

//        String[] types2 = {"FloatValue", "FloatValue", "FloatValue"};
//        String[] names2 = {"AA", "BB", "CC"};
////
//        DataFrame dataFrame1 = null;

//        try {
//            dataFrame1 = new DataFrame("src/main/resources/data.csv", types2, true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        DataFrameUtils.dropDatabase(dataFrame1,10);


        SparseDataFrame sparseDataFrame = null;
//        try {
//            sparseDataFrame = new SparseDataFrame(dataFrame, "dupa");
//        } catch (WrongInsertionTypeException e) {
//            e.printStackTrace();
//        }

//        try {
//            sparseDataFrame = new SparseDataFrame("src/main/resources/sparse.csv", types2,true, "0.0");
//        } catch (Exception e) {
//            System.out.println("EEEE");
//            e.printStackTrace();
//        }


//        System.out.println(sparseDataFrame.getCols());


//        sparseDataFrame.dropDatabase(10);

//        dropDatabase(sparseDataFrame, 10);

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        BorderPane borderPane = new BorderPane();
//        scene.getStylesheets().add(getClass().toString());


        Line line = new Line();


        line.setStartX(100.0);
        line.setStartY(150.0);

        line.setEndX(500.0);
        line.setEndY(150.0);

        Group group = new Group(line);

        Scene scene = new Scene(group, 800, 800);
        scene.setFill(Color.BLUE);


        primaryStage.setTitle("DataFrame");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
