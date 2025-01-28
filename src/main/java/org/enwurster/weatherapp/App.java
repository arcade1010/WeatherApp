package org.enwurster.weatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.CheckBox;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {
    //Application's start(Stage) method is invoked
    public static void main(String[] args) {
        launch();
    }

    //It's invoked here
    @Override
    public void start(Stage stage) throws IOException {
        //FXML and stage title
        FXMLLoader fxmlLoader = new FXMLLoader(NewMain.class.getResource("hello-view.fxml"));
        stage.setTitle("WeatherApp!");


        //Checkboxes
        CheckBox box1 = new CheckBox("temp");
        CheckBox box2 = new CheckBox("temp_min");
        CheckBox box3 = new CheckBox("temp_max");
        CheckBox box4 = new CheckBox("pressure");
        CheckBox box5 = new CheckBox("humidity");
        //add the CheckBoxes to checkBoxes, which will be sent to the toString() and beginProcess() as checkboxes (as a list of strings)
        ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
        checkBoxes.add(box1);
        checkBoxes.add(box2);
        checkBoxes.add(box3);
        checkBoxes.add(box4);
        checkBoxes.add(box5);
        //back to weather button
        Button btwButton = new Button("Back to the weather");
        //layout for checkbox menu
        VBox layoutMenu =  new VBox(20);
        layoutMenu.getChildren().addAll(box1, box2, box3, box4, box5, btwButton);
        Scene sceneMenu = new Scene(layoutMenu, 400, 300);

        //stuff to add to layout
        TextField nameInput = new TextField();
        //button to go to menu page
        Button menuButton = new Button("Go to checkboxes");
        //will go under button btw
        Label l1 = new Label("Weather info here:");
        //b's toString method down below, return's a single string of weather data or pop's an alert box if invalid
        Button b = new Button("Click me!");
        b.setOnAction( e -> l1.setText("Here's the weather:) " + isString(nameInput, nameInput.getText(), checkBoxes)));

        //Make Layout
        VBox layout1 = new VBox(10);
        layout1.setPadding(new Insets(20, 20, 20, 20));
        layout1.getChildren().addAll(menuButton, nameInput, b, l1);

        //Make scene w/ layout, set the scene and show the stage
        Scene scene = new Scene(layout1, 400, 300);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        //Buttons for switching scene (weather - menu)
        menuButton.setOnAction(e -> stage.setScene(sceneMenu));
        btwButton.setOnAction(e -> stage.setScene(scene));
    }

    //If a valid String, return the HashMap of weather data fields according to the checkboxes, in invalid -> pop the alert box
    private String isString(TextField input, String name, List<CheckBox> checkBoxes){
        try{
            //Remove all whitespace and set to lowercase so Geocoder API can read the city
            name = name.replaceAll("\\s+", "").trim().toLowerCase();
            System.out.println(name);
            char[] chars = name.toCharArray();
            //Pop an AlertBox if any strange scary bad stuff is detected in user input
            for (char c : chars) {
                if(!Character.isLetter(c)) {
                    AlertBox.display("Hey", name);
                    return "";
                }
            }
            //Confirmed valid so lets check the checkboxes to alter what data is displayed
            //Make list of checked checkboxes and pass it to beginProcess() which will return a HashMap of all the data we want to show
            //Take the List checkBoxes and add each checked box's text to the ArrayList checkboxes. (I should probably rename)
            ArrayList<String> checkboxes = new ArrayList<>();
            for (CheckBox cb : checkBoxes) {
                if (cb.isSelected()) {
                    checkboxes.add(cb.getText());
                }
            }

            //It's a valid string so run the APIs!!
            Controller controller = new Controller(name);
            return (controller.beginProcess(checkboxes).toString());

        } catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }


}

