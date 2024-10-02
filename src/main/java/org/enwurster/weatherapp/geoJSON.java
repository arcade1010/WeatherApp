package org.enwurster.weatherapp;
//Contains all relevant data fields from the JSON object returning from the Geocoder API call. Used to make a geoJSON object.
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true) //Allows me to ignore data fields from the api call I don't care about (local names)
public class geoJSON {
    private String name;
    private double lat;
    private double lon;
    private String country;
    private String state;
    public geoJSON(){};

    public String getName() { return name; }
    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }
    public String getCountry(){
        return country;
    }
    public String getState(){
        return state;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setState(String state) {
        this.state = state;
    }


    @Override
    public String toString(){
        return ("City: " + name + ", Country: " + country + ", State: " + state + ", lon: " + lon + ", lat: " + lat);
    }
}
