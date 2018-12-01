package com.obiektowe;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main(String[] args) {

        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
