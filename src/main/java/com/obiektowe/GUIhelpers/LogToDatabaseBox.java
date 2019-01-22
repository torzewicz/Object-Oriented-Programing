package com.obiektowe.GUIhelpers;

import com.obiektowe.classes.DataFrameDB;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LogToDatabaseBox {

    public static void display(DataFrameDB dataFrameDB) {
        Stage window = new Stage();
        window.initStyle(StageStyle.UTILITY);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(300);
        window.setMinHeight(180);

        GridPane gridPane = new GridPane();

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(8);
        gridPane.setHgap(10);

        Text username = new Text("Username:");
        Text password = new Text("Password:");
        Text database = new Text("Database name:");

        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        TextField databaseField = new TextField();

        Button submit = new Button("Submit");

        submit.setOnAction(i -> {
            boolean validation = validateCredentials(usernameField.getText(), passwordField.getText(), databaseField.getText(), dataFrameDB);
            if (validation) {
                window.close();
            } else {
                AlertBox.display("Error", "Please provide valid credentials");
            }
        });

        GridPane.setConstraints(username, 0, 0);
        GridPane.setConstraints(password, 0, 1);
        GridPane.setConstraints(database, 0, 2);
        GridPane.setConstraints(usernameField, 1, 0);
        GridPane.setConstraints(passwordField, 1, 1);
        GridPane.setConstraints(databaseField, 1, 2);

        GridPane.setColumnSpan(submit, 2);
        GridPane.setConstraints(submit, 0, 3);
        GridPane.setHalignment(submit, HPos.CENTER);

        gridPane.getChildren().addAll(username, password, usernameField, passwordField, database, databaseField, submit);

        Scene scene = new Scene(gridPane, 250, 120);
        window.setScene(scene);
        window.showAndWait();
    }

    private static boolean validateCredentials(String username, String password, String databaseName, DataFrameDB dataFrameDB) {
        boolean done;
        try {
            dataFrameDB.connect(databaseName, username, password);
            done = true;
        } catch (Exception e) {
            done = false;
            e.printStackTrace();
        }
        return done;
    }
}
