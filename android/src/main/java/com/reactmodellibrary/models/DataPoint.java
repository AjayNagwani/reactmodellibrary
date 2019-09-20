package com.reactmodellibrary.models;

public class DataPoint {

    private String location;
    private String color;

    public DataPoint(String location, String color){
        this.location = location;
        this.color = color;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public void setColor(String color){
        this.color = color;
    }

    public String getLocation(){
        return  this.location;
    }

    public String getColor(){
        return  this.color;
    }
}
