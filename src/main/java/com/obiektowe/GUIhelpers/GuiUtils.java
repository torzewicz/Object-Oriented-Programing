package com.obiektowe.GUIhelpers;

import com.obiektowe.classes.Col;
import com.obiektowe.classes.DataFrame;
import com.obiektowe.classes.Exceptions.NotEqualListsSizeException;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;
import com.obiektowe.classes.GroupedDF;
import com.obiektowe.classes.Interfaces.Applyable;
import com.obiektowe.classes.Value.Value;
import com.sun.imageio.plugins.gif.GIFImageReader;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.obiektowe.GUIhelpers.AlertBox.display;

public class GuiUtils {

    public static Pair<ScrollPane, DataFrame> displayDataFrame(Stage window) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource");

        File file = fileChooser.showOpenDialog(window);

        boolean isCorrectForm = false;

        DataFrame currentDataFrame = null;

        while(!isCorrectForm) {
            try {

                String[] necessaryKnowledge = handlePrecautions(file);
                currentDataFrame = new DataFrame(file.getAbsolutePath(), necessaryKnowledge, true);
                isCorrectForm = true;
            } catch (Exception e) {
                display("Error", "Could not create DataFrame with provided types");
                e.printStackTrace();
            }
        }

        ScrollPane scrollPane = createScrollPaneAndGridPane(currentDataFrame, true).getKey();
        return new ImmutablePair<>(scrollPane, currentDataFrame);
    }

    private static Pair<ScrollPane, GridPane> createScrollPaneAndGridPane(DataFrame currentDataFrame, boolean displayType)  {
        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);
        int currentColumn = 0;
        for (Col col : currentDataFrame.getCols()) {

            Text name = new Text(col.getName());
            GridPane.setConstraints(name, currentColumn, 0);
            GridPane.setHalignment(name, HPos.CENTER);
            gridPane.getChildren().add(name);

            if (displayType) {
                Text type = new Text(col.getType());
                GridPane.setConstraints(type, currentColumn, 1);
                GridPane.setHalignment(type, HPos.CENTER);
                gridPane.getChildren().add(type);
            }


            int currentRow = 2;
            for (Object object : col.getObjects()) {
                Text currentObject = new Text(object.toString());

                GridPane.setConstraints(currentObject, currentColumn, currentRow);
                GridPane.setHalignment(currentObject, HPos.CENTER);

                gridPane.getChildren().add(currentObject);
                currentRow++;
            }

            currentColumn++;
        }
        gridPane.setAlignment(Pos.TOP_CENTER);
        scrollPane.setContent(gridPane);
        return new ImmutablePair<>(scrollPane, gridPane);
    }

    public static void handleExit(Event windowEvent, Stage stage) {
        windowEvent.consume();
        boolean answer = ConfirmBox.display("Exit window", "Are you sure?");
        if (answer) {
            stage.close();
        }
    }

    private static String[] handlePrecautions(File file) {
        String strLine = "";
        String[] dataString;
        try {
            FileInputStream fileInputStream = new FileInputStream(new File(file.getAbsolutePath()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            strLine = bufferedReader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }

        dataString = PreDataFrameBox.display(strLine.split(","));

        return dataString;
    }

    public static GridPane prepareUtils(DataFrame dataFrame, BorderPane borderPane, Stage window) {

        List<Pair<String, String>> operations = Arrays.asList(
                new ImmutablePair<>("Maximum", "max"),
                new ImmutablePair<>("Minimum", "min"),
                new ImmutablePair<>("Mean", "mean"),
                new ImmutablePair<>("Standard", "std"),
                new ImmutablePair<>("Sum", "sum"),
                new ImmutablePair<>("Variance", "var")
        );

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        TextField colName = new TextField();

        Button button = new Button("Group by:");

        GridPane.setConstraints(button, 0, 0);
        GridPane.setHalignment(button, HPos.CENTER);

        GridPane.setConstraints(colName, 1, 0);
        GridPane.setHalignment(colName, HPos.CENTER);

        Text currentStateMessage = new Text("Perform operation on whole DataFrame:");

        GridPane.setConstraints(currentStateMessage, 0, 2);
        GridPane.setHalignment(currentStateMessage, HPos.CENTER);
        GridPane.setColumnSpan(currentStateMessage, 3);

        int currentRow = 3;
        int currentColumn = 0;
        for (Pair<String, String> pair : operations) {
            Button operationButton = new Button(pair.getLeft());
            GridPane.setConstraints(operationButton, currentColumn, currentRow);
            GridPane.setHalignment(operationButton, HPos.CENTER);
            currentColumn++;
            operationButton.setMinSize(80,20);

            if (operations.indexOf(pair) > 0 && (operations.indexOf(pair)  + 1) % 3 == 0) {
                currentRow ++;
                currentColumn = 0;
            }

            operationButton.setOnAction(i -> borderPane.setCenter(displayDesiredOperation(dataFrame, pair.getKey())));

            gridPane.getChildren().add(operationButton);
        }

        button.setOnAction(i -> {
            if (validateCorrectness(colName, dataFrame)) {
                try {
                    Pair<ScrollPane, GroupedDF> groupedDFPair = displayGroupedDataFrames(colName.getText(), dataFrame);
                    borderPane.setCenter(groupedDFPair.getKey());
                    adaptUtilsView(groupedDFPair.getValue(), operations, borderPane);
                } catch (NotEqualListsSizeException | WrongInsertionTypeException e) {
                    e.printStackTrace();
                }
            } else {
                display("Error", "Please provide valid column name");
            }
        });


        //Added button to display 2d chart

        Button switchTo2dChartView = new Button("Show 2D chart view");

        GridPane.setColumnSpan(switchTo2dChartView, 3);
        GridPane.setConstraints(switchTo2dChartView, 0, currentRow + 1);
        GridPane.setHalignment(switchTo2dChartView, HPos.CENTER);

        switchTo2dChartView.setOnAction(i -> {
            showNewSceneWithChart(borderPane, window, dataFrame);
        });

        gridPane.getChildren().addAll(button, colName, switchTo2dChartView, currentStateMessage);

        return gridPane;
    }

    private static boolean validateCorrectness(TextField textField, DataFrame dataFrame) {
        boolean answer = false;

        for (Col col : dataFrame.getCols()) {
            if (col.getName().equals(textField.getText())) {
                answer = true;
            }
        }

        return answer;
    }

    private static Pair<ScrollPane,GroupedDF> displayGroupedDataFrames(String colName, DataFrame currentDataFrame) throws NotEqualListsSizeException, WrongInsertionTypeException {
        ScrollPane scrollPane = new ScrollPane();
        VBox vBox = new VBox();
        GroupedDF groupedDF = currentDataFrame.groupby(colName);

        for (DataFrame dataFrame : groupedDF.getDataFrames()) {
            vBox.getChildren().add(createScrollPaneAndGridPane(dataFrame, false).getValue());
        }
        scrollPane.setContent(vBox);
        return new ImmutablePair<>(scrollPane, groupedDF);
    }

    private static void adaptUtilsView(GroupedDF groupedDF, List<Pair<String, String>> operations, BorderPane borderPane) {
        GridPane gridPane = ((GridPane)borderPane.getLeft());
        ((Text)gridPane.getChildren().get(gridPane.getChildren().size() - 1)).setText("Perform operation on columns without: " + groupedDF.groupedByCols[0]);
        for(Node node : gridPane.getChildren()) {
            if (node.getClass().getSimpleName().equals("Button") && !((Button)node).getText().equals("Group by:")) {
                for (Pair<String, String> pair : operations) {
                    if (((Button)node).getText().equals(pair.getKey())) {
                        ((Button) node).setOnAction(i -> {
                            GridPane dataFrameAfterOperationWithoutCol = createCustomGridPaneForGroupedOperations(groupedDF, pair);
                            borderPane.setCenter(dataFrameAfterOperationWithoutCol);
                        });
                    }
                }

            }
        }

    }

    private static GridPane createCustomGridPaneForGroupedOperations(GroupedDF groupedDF, Pair<String, String> pair) {
        Method method = null;
        DataFrame dataFrame = null;
        try {
            method = groupedDF.getClass().getMethod(pair.getValue());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        try {
            dataFrame = (DataFrame) method.invoke(groupedDF);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        GridPane dataFrameAfterOperationWithoutCol = new GridPane();

        dataFrameAfterOperationWithoutCol.setPadding(new Insets(10, 10, 10, 10));
        dataFrameAfterOperationWithoutCol.setVgap(8);
        dataFrameAfterOperationWithoutCol.setHgap(10);

        int columnNumber = 0;
        for (Col col : dataFrame.getCols()) {
            Text name = new Text(col.getName());

            Text object = new Text(col.getObjects().get(0).toString());

            GridPane.setConstraints(name, columnNumber, 0);
            GridPane.setHalignment(name, HPos.CENTER);
            GridPane.setConstraints(object, columnNumber, 1);
            GridPane.setHalignment(object, HPos.CENTER);

            columnNumber++;

            dataFrameAfterOperationWithoutCol.getChildren().addAll(name, object);

        }

        return dataFrameAfterOperationWithoutCol;
    }

    private static GridPane displayDesiredOperation(DataFrame dataFrame, String className) {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);
        String stringInstance = "com.obiektowe.classes.Applyable." + className;
        Class instance = null;
        try {
            instance = Class.forName(stringInstance);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        DataFrame dataFrameAfterOperation = null;

        try {
            dataFrameAfterOperation = dataFrame.groupby(dataFrame.getCols().get(0).getName()).apply((Applyable) instance.newInstance());
        } catch (NotEqualListsSizeException e) {
            e.printStackTrace();
        } catch (WrongInsertionTypeException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        int colNumber = 0;
        for (Col col : dataFrameAfterOperation.getCols()) {
            Text name = new Text(col.getName());
            Text type = new Text(col.getType());
            Text object = new Text(col.getObjects().get(0).toString());

            GridPane.setConstraints(name, colNumber, 0);
            GridPane.setHalignment(name, HPos.CENTER);
            GridPane.setConstraints(type, colNumber, 1);
            GridPane.setHalignment(type, HPos.CENTER);
            GridPane.setConstraints(object, colNumber, 2);
            GridPane.setHalignment(object, HPos.CENTER);

            gridPane.getChildren().addAll(name, type, object);

            colNumber++;

        }

        return gridPane;
    }

    private static void showNewSceneWithChart(BorderPane borderPane, Stage window, DataFrame dataFrame) {
        BorderPane chartBorderPane = new BorderPane();
        chartBorderPane.setTop(borderPane.getTop());

        Scene oldScene = window.getScene();
        GridPane leftGridPane = new GridPane();

        leftGridPane.setPadding(new Insets(10, 10, 10, 10));
        leftGridPane.setVgap(8);
        leftGridPane.setHgap(10);

        String[] possibilities = dataFrame.getCols().stream().map(i -> i.getName()).collect(Collectors.toList()).toArray(new String[dataFrame.getCols().size()]);
        String[] xColYCol = new String[2];

        ChoiceBox<String>[] choiceBoxes = new ChoiceBox[2];
        Button submitButton = new Button("Submit");

        submitButton.setOnAction(i -> {
            for (int a = 0; a < choiceBoxes.length; a++) {
                xColYCol[a] = choiceBoxes[a].getValue();
            }

            chartBorderPane.setCenter(display2DChart(dataFrame, xColYCol));
        });
        GridPane.setColumnSpan(submitButton, 3);
        GridPane.setConstraints(submitButton, 0, 2);
        GridPane.setHalignment(submitButton, HPos.CENTER);


        for (int i =0; i < 2; i++ ) {
            Text text = i == 0 ? new Text("Set Col for X axis:") : new Text("Set Col for Y axis:");
            GridPane.setConstraints(text, 0, i);
            GridPane.setHalignment(text, HPos.CENTER);
            choiceBoxes[i] = new ChoiceBox<>();
            choiceBoxes[i].getItems().addAll(possibilities);
            choiceBoxes[i].setValue(possibilities[0]);
            GridPane.setConstraints(choiceBoxes[i], 1, i);
            leftGridPane.getChildren().addAll(text, choiceBoxes[i]);
        }

        leftGridPane.getChildren().add(submitButton);

        Button backToPreviousViewButton = new Button("Back to previous view");

        backToPreviousViewButton.setOnAction(i -> {
            window.setScene(oldScene);
        });


        GridPane.setColumnSpan(backToPreviousViewButton, 3);
        GridPane.setConstraints(backToPreviousViewButton, 0, 3);
        GridPane.setHalignment(backToPreviousViewButton, HPos.CENTER);

        leftGridPane.getChildren().add(backToPreviousViewButton);

        chartBorderPane.setLeft(leftGridPane);

        Scene scene = new Scene(chartBorderPane, 1120, 630);

        window.setScene(scene);
    }

    private static LineChart display2DChart(DataFrame dataFrame, String[] xAndY) {

        Col xCol = dataFrame.get(xAndY[0]);
        Col yCol = dataFrame.get(xAndY[1]);

        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();


        LineChart lineChart = new LineChart(xAxis, yAxis);

        lineChart.setTitle("New Chart");

        XYChart.Series series = new XYChart.Series();

        series.setName("Data");

        for (int i = 0; i < xCol.getObjects().size(); i++) {
            series.getData().add(new XYChart.Data(((Value)xCol.getObjects().get(i)).getValue(), ((Value)yCol.getObjects().get(i)).getValue()));
        }

        lineChart.getData().add(series);


        return lineChart;

    }

}
