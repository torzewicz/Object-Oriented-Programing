package com.obiektowe.GUIhelpers;

import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class AlertBox {

    public static void display(String title, String message) {
        Stage window = new Stage();
        window.initStyle(StageStyle.UTILITY);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);

        window.setOnCloseRequest(Event::consume);

        Button closeButton = new Button("Close Alert");
        closeButton.setOnAction(i -> window.close());

        VBox layout = new VBox(15);

        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 220, 100);
        window.setScene(scene);
        window.showAndWait();
    }

}
