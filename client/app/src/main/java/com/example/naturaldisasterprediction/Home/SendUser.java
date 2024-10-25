package com.example.naturaldisasterprediction.Home;

import com.example.naturaldisasterprediction.SignUp.User;

import java.time.LocalDate;
import java.util.List;

public class SendUser {
    private String HealthName;
    private String HealthGender;// 1 = male, 2 = female
    private Integer HealthAge;
    private Float HealthWeight;
    private Float HealthHeight;

    public String getHealthGender() {
        return HealthGender;
    }

    public void setHealthGender(String healthGender) {
        HealthGender = healthGender;
    }

    public Integer getHealthAge() {
        return HealthAge;
    }

    public void setHealthAge(Integer healthAge) {
        HealthAge = healthAge;
    }

    public Float getHealthWeight() {
        return HealthWeight;
    }

    public void setHealthWeight(Float healthWeight) {
        HealthWeight = healthWeight;
    }

    public Float getHealthHeight() {
        return HealthHeight;
    }

    public void setHealthHeight(Float healthHeight) {
        HealthHeight = healthHeight;
    }

    public String getHealthName() {
        return HealthName;
    }

    public void setHealthName(String healthName) {
        HealthName = healthName;
    }
}
