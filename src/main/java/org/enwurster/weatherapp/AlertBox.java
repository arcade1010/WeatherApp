package org.enwurster.weatherapp;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
public class AlertBox {

    public static void display(String title, String message){
        Stage window = new Stage();

        //Blocks interaction with other windows until this window is closed
        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Hey!");
        window.setWidth(250);


        //Creating a new window with w/ label, close button.
        Label label = new Label();
        label.setText("\"" + message + "\" has a number in it :(");
        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(label, closeButton);
        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.show();

    }
}
