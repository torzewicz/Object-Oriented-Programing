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

public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();
//        window.initStyle(StageStyle.UNDECORATED);
        window.initStyle(StageStyle.UTILITY);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);


        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        window.setOnCloseRequest(Event::consume);

        yesButton.setOnAction(i -> {
            answer = true;
            window.close();
        });

        noButton.setOnAction(i -> {
            answer = false;
            window.close();
        });



        VBox layout = new VBox(15);

        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 250, 120);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
