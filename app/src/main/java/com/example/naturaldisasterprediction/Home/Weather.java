package com.example.naturaldisasterprediction.Home;

public class Weather{
    private int temp;
    private int id;
    private String time;

    public Weather(int temp, int id, String time){
        this.temp = temp;
        this.id = id;
        this.time = time;
    }

    public int getTemp() {
        return temp;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
