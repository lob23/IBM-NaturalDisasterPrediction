package com.example.naturaldisasterprediction.Models.User;

import com.example.naturaldisasterprediction.Models.GPSLocation;

import java.util.Date;

public class UserUpdateLocationRequest {
    GPSLocation location;
    String token;
    Date updateDate;

    public UserUpdateLocationRequest(GPSLocation location, String token, Date updateDate) {
        this.location = location;
        this.token = token;
        this.updateDate = updateDate;
    }

    public GPSLocation getLocation() {
        return location;
    }

    public void setLocation(GPSLocation location) {
        this.location = location;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
