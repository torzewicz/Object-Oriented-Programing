package com.obiektowe;

import com.obiektowe.GUIhelpers.AlertBox;
import com.obiektowe.GUIhelpers.ConfirmBox;
import com.obiektowe.GUIhelpers.PreDataFrameBox;
import com.obiektowe.classes.Col;
import com.obiektowe.classes.DataFrame;
import com.obiektowe.classes.Exceptions.NotEqualListsSizeException;
import com.obiektowe.classes.Exceptions.WrongInsertionTypeException;
import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.util.Arrays;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage window;
    private Scene scene;
    private FileChooser fileChooser;
    private DataFrame currentDataFrame;
    private BorderPane borderPane;
    private GridPane gridPane;
    private ScrollPane scrollPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("OOP");

        borderPane = new BorderPane();

        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource");

        window.setOnCloseRequest(i -> handleExit(i, window));

//        Stop[] stops = new Stop[] { new Stop(0, Color.BLACK), new Stop(1, Color.RED)};
//        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
//        Rectangle rectangle = new Rectangle(160, 110);
//        rectangle.setFill(lg1);
//        leftSide.getChildren().addAll(rectangle);


        Menu fileMenu = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu view = new Menu("View");
        Menu help = new Menu("Help");


        MenuItem newDataFrame = new MenuItem("New DataFrame");
        scrollPane = new ScrollPane();
        gridPane = new GridPane();
        newDataFrame.setOnAction(i -> {
            File file = fileChooser.showOpenDialog(window);

            String[] necessaryKnowledge = handlePrecautions(file);
            System.out.println("Hej: " + necessaryKnowledge);

            try {
                currentDataFrame = new DataFrame(file.getAbsolutePath(), necessaryKnowledge, true);
                gridPane.setPadding(new Insets(10, 10, 10, 10));
                gridPane.setVgap(8);
                gridPane.setHgap(10);
                int currentColumn = 0;
                for (Col col : currentDataFrame.getCols()) {
                    Text name = new Text(col.getName());
                    Text type = new Text(col.getType());

                    GridPane.setConstraints(name, currentColumn, 0);
                    GridPane.setHalignment(name, HPos.CENTER);

                    GridPane.setConstraints(type, currentColumn, 1);
                    GridPane.setHalignment(type, HPos.CENTER);

                    gridPane.getChildren().addAll(name, type);

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
                borderPane.setRight(scrollPane);
                window.setScene(scene);
                window.show();
            } catch (IOException | NotEqualListsSizeException | WrongInsertionTypeException | IllegalAccessException | InstantiationException e) {
                e.printStackTrace();
            }

        });

        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(i -> handleExit(i, window));

        MenuItem about = new MenuItem("About");

        about.setOnAction(i -> getHostServices().showDocument("https://github.com/torzewicz"));

        fileMenu.getItems().addAll(newDataFrame, new MenuItem("Open DataFrame"), new SeparatorMenuItem(), exitMenuItem);
        edit.getItems().add(new MenuItem("Set Header"));
        view.getItems().add(new MenuItem("Display DataFrame"));
        help.getItems().add(about);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, edit, view, help);

        borderPane.setTop(menuBar);

        scene = new Scene(borderPane, 1120, 630);


        window.setScene(scene);
        window.show();
    }

    private void handleExit(Event windowEvent, Stage stage) {
        windowEvent.consume();
        boolean answer = ConfirmBox.display("Exit window", "Are you sure?");
        if (answer) {
            stage.close();
        }
    }

    private void openFile(File file) {
        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        } catch (IOException e) {
            AlertBox.display("File opening error", file.getName() + " can not be open");
        }
    }

    private String[] handlePrecautions(File file) {
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

}
