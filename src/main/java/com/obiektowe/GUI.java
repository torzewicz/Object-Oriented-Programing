package com.obiektowe;

import com.obiektowe.GUIhelpers.AlertBox;
import com.obiektowe.GUIhelpers.ConfirmBox;
import javafx.application.Application;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }


    Stage window;
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("OOP");

        window.setOnCloseRequest(i -> handleExit(i, window));

        Button alertButton = new Button("Create alert");
        alertButton.setOnAction(i -> AlertBox.display("Simple alert box", "Wow this works"));

        Button confirmButton = new Button("Next box");
        confirmButton.setOnAction(i -> {
            boolean result = ConfirmBox.display("Simple confirm box", "Are you sure");
            System.out.println(result);
        });

//
//        Stop[] stops = new Stop[] { new Stop(0, Color.BLACK), new Stop(1, Color.RED)};
//        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
//        Rectangle rectangle = new Rectangle(160, 110);
//        rectangle.setFill(lg1);
//        leftSide.getChildren().addAll(rectangle);


        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu view = new Menu("View");
        Menu help = new Menu("Help");


        MenuItem newDataFrame = new MenuItem("New DataFrame");

        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(i -> handleExit(i, window));

        MenuItem about = new MenuItem("About");

        about.setOnAction(i -> getHostServices().showDocument("https://github.com/torzewicz"));

        file.getItems().addAll(newDataFrame, new MenuItem("Open DataFrame"), new SeparatorMenuItem(), exitMenuItem);
        edit.getItems().add(new MenuItem("Set Header"));
        view.getItems().add(new MenuItem("Display DataFrame"));
        help.getItems().add(about);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(file, edit, view, help);


        BorderPane borderPane = new BorderPane();

        borderPane.setTop(menuBar);


        scene = new Scene(borderPane, 1120, 630);


        window.setScene(scene);
        window.show();
    }

    private void handleExit(Event windowEvent, Stage stage) {
        windowEvent.consume();
        boolean answer = ConfirmBox.display("Exit window", "Are you sure?");
        if(answer) {
            stage.close();
        }
    }


}
