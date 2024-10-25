package com.example.naturaldisasterprediction.SignUp;

import android.os.Build;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private Integer gender; // 1 = male, 2 = female
    private LocalDate birth;
    private Float weight;
    private Float height;
    private List<User> family;
    private String phone;
    private String email;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public User(){
        this.name = "Shiba";
        this.gender = 2;
        this.birth = LocalDate.of(2024, Month.OCTOBER, 16);
        this.weight = 0.0F;
        this.height = 0.0F;
        this.family = new ArrayList<>();
        this.phone = "1122334455";
        this.email = "trantran0603";
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setGender(Integer gender){
        this.gender = gender;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public void setFamily(List<User> family) {
        this.family = family;
    }
    public void setEmail(String email) {this.email = email; }
    public void setPhone(String phone) {this.phone = phone; }
    public String getName(){ return this.name; }
    public Integer getGender(){return this.gender; }
    public LocalDate getBirth(){return this.birth; }
    public Float getWeight(){return this.weight; }
    public Float getHeight(){return this.height; }
    public List<User> getFamily(){return this.family; }
    public String getMail(){ return this.email; }
    public String getPhone(){ return this.phone; }

    public JSONObject toJson(){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("UserName", this.name);
            jsonObject.put("UserEmail", this.email);
            jsonObject.put("UserContactNumber", this.phone);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
