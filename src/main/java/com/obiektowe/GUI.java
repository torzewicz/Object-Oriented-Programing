package com.obiektowe;

import com.obiektowe.classes.DataFrame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.apache.commons.lang3.tuple.Pair;

import static com.obiektowe.GUIhelpers.GuiUtils.*;

public class GUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private Stage window;
    private Scene scene;
    private BorderPane borderPane;

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("OOP");

        borderPane = new BorderPane();

        window.setOnCloseRequest(i -> handleExit(i, window));

        Menu fileMenu = new Menu("File");
        Menu edit = new Menu("Edit");
        Menu view = new Menu("View");
        Menu help = new Menu("Help");


        MenuItem newDataFrame = new MenuItem("New DataFrame");

        newDataFrame.setOnAction(i -> {
            Pair<ScrollPane, DataFrame> currentWorkBench = null;
            try {
                currentWorkBench = displayDataFrame(window);
            } catch (Exception e) {
                e.printStackTrace();
            }
            borderPane.setRight(currentWorkBench.getKey());
            borderPane.setLeft(prepareUtils(currentWorkBench.getValue(), borderPane, window));
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


}
