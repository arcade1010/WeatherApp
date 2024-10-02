module org.enwurster.weatherapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens org.enwurster.weatherapp to javafx.fxml;
    exports org.enwurster.weatherapp;
}