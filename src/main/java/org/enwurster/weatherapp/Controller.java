package org.enwurster.weatherapp;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import java.util.List;
import java.util.Stack;
import java.util.HashMap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Label;

public class Controller {
    //Most or all of the logic for the program, should be moved to a Controller.java class and Main.java should be just for gui initialization
    ObjectMapper mapper = new ObjectMapper();

    //API call info
    private String APIKey = "a00543a1dfefe1481476d9ef86e46c59";
    String cityName;
    String urlString;

    public Controller(String cityName) {
        this.cityName = cityName;
        this. urlString = "http://api.openweathermap.org/geo/1.0/direct?q=" + cityName + "&limit=5&appid=" + getAPIKey();
    }
    private String getAPIKey(){
        return this.APIKey;

    }


    //Want to make this return a HashMap weather descriptions to data (temp to 74, feels_like to 75)
    public HashMap<String, String> beginProcess(List<String> checkboxes){
        try {
            System.out.println("Beginning process with this cityName: " + cityName);
            //New geoJSON java object to store json geocoder data from geocoder api
            geoJSON gJSON = new geoJSON();
            //Begin method chain to ask geocoder API for data about the city, eventually returning a geoJSON object that has the data we need
            gJSON = getLocationData(urlString);

            System.out.println("Here's the geocoder location stuff: " + gJSON.toString());
            //call and display weather information from OpenWeatherMap api using lon and lat coords from gJSON
            return displayWeatherData(gJSON.getLat(), gJSON.getLon(), checkboxes);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //returns first index of list of geoJSON objects that is produced by the geocoder api
    private static geoJSON getLocationData(String urlString) {

        try {
            //Make apiConnection from returned HttpURLConnection object
            HttpURLConnection apiConnection = fetchAPIResponse(urlString);

            //Check to see if api is connected/responsive
            if(apiConnection.getResponseCode() != 200) {
                System.out.println("Couldn't connect to API");
                return null;
            }
            //Make String that holds all JSON data from api call (will be an array within a string
            String jsonResponse = readAPIResponse(apiConnection);

            //Create mapper and gJSON
            ObjectMapper mapper = new ObjectMapper();
            geoJSON gJSON = new geoJSON();


            //New array of geoJSONs, read the Array of JSON objects from the string into the array of geoJSONs
            geoJSON[] gJSONArray = mapper.readValue(jsonResponse, geoJSON[].class);
            //Convert array of geoJSONs to a list
            List<geoJSON> geoJSONList = Arrays.asList(gJSONArray);

            //return first index / first JSON object in list (that's all we need)
            return geoJSONList.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return null is an error occurs
        return null;
    }

    //get and return the api connection
    private static HttpURLConnection fetchAPIResponse(String urlString){
        try {
            //New url object made from url String
            URL u = new URL(urlString);
            //Make HttpURLConnection object conn from the url objects openConnection() method
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();

            conn.setRequestMethod("GET");
            return conn;

        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    //Return String holding JSON data coming from api call
    private static String readAPIResponse(HttpURLConnection apiConnection){
        try{
            //StringBuilder object to continuously append new data from the API scanner
            StringBuilder resultJSON = new StringBuilder();

            //Make new scanner with its input parameter being the apiConnection objects getInputStreamMethod
            Scanner sc = new Scanner(apiConnection.getInputStream());

            //Add all data from api call to the StringBuilder object
            while(sc.hasNext()){
                resultJSON.append(sc.nextLine());
            }
            sc.close(); //Close scanner
            //Method returns string so return the StringBuilder object as a string
            return resultJSON.toString();

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //Returns the toString to display on the label
    private static HashMap<String, String> displayWeatherData(double latitude, double longitude, List<String> checkboxes){
        //OpenWeatherMap API url with key
        String url = "https://api.openweathermap.org/data/2.5/weather?lat="+ latitude + "&lon=" + longitude +
                "&appid=a00543a1dfefe1481476d9ef86e46c59";
        try {
            HttpURLConnection apiConnection = fetchAPIResponse(url);

            //Check to see if api is connected/responsive
            if(apiConnection.getResponseCode() != 200) {
                System.out.println("Couldn't connect to API");
            }

            String jsonResponse = readAPIResponse(apiConnection);

            ObjectMapper mapper = new ObjectMapper();
            weatherJSON wJSON = new weatherJSON();
            //mapper Maps the incoming JSON Array, to the List of weather objects in wJSON.
            wJSON = mapper.readValue(jsonResponse, weatherJSON.class);


            return wJSON.getMain().toHashMap(checkboxes);

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}