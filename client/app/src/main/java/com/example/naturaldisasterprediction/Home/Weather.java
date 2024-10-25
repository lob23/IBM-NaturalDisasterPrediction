package com.example.naturaldisasterprediction.Home;

public class Weather{
    private int temp;
    private String id;
    private String time;

    public Weather(int temp, String id, String time){
        this.temp = temp;
        this.id = id;
        this.time = time;
    }

    public int getTemp() {
        return temp;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
