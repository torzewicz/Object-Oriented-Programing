package com.obiektowe.GUIhelpers;

import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Arrays;

public class PreDataFrameBox {

    public static String[] display(String[] dataString) {
        Stage window = new Stage();
        window.initStyle(StageStyle.UTILITY);
        String[] answer = new String[dataString.length];

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("DataFrame Initializer");
        window.setMinWidth(400);

        window.setOnCloseRequest(Event::consume);

        String[] possibilities = {
                "BooleanValue",
                "DateTimeValue",
                "DoubleValue",
                "FloatValue",
                "IntegerValue",
                "StringValue"
        };

        ChoiceBox<String>[] choiceBoxes = new ChoiceBox[dataString.length];
        Button button = new Button("Submit");

        button.setOnAction(i -> {
            for (int a = 0; a < choiceBoxes.length; a++) {
                answer[a] = choiceBoxes[a].getValue();
            }
            window.close();
        });

        GridPane gridPane = new GridPane();
        Text mainText = new Text("Select values for columns");

        GridPane.setColumnSpan(mainText, dataString.length + 1);
        GridPane.setHalignment(mainText, HPos.CENTER);
        gridPane.getChildren().add(mainText);

        GridPane.setConstraints(button, 0, 4);
        gridPane.getChildren().add(button);


        for (int i = 0; i < dataString.length; i++) {
            Text text = new Text(dataString[i]);
            GridPane.setConstraints(text, i, 1);
            GridPane.setHalignment(text, HPos.CENTER);
            choiceBoxes[i] = new ChoiceBox<>();
            choiceBoxes[i].getItems().addAll(possibilities);
            choiceBoxes[i].setValue("StringValue");
            GridPane.setConstraints(choiceBoxes[i], i, 2);
            gridPane.getChildren().addAll(text, choiceBoxes[i]);

        }

        Scene scene = new Scene(gridPane, 400, 250);
        window.setScene(scene);
        window.showAndWait();
        return answer;
    }


}
