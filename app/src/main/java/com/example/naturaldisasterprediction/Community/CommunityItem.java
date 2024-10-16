package com.example.naturaldisasterprediction.Community;

import android.content.Context;

import java.io.Serializable;

public class CommunityItem implements Serializable {
    private String name;
    private String userID;
    private String imageResourceId;
    private String text;


    public CommunityItem(String name, String userID, String imageResourceId, String text) {
        this.name = name;
        this.userID = userID;
        this.imageResourceId = imageResourceId;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public String getUserID() {
        return userID;
    }

    public String getImageResourceId() {
        return imageResourceId;
    }

    public String getText() {
        return text;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setImageResourceId(String imageResourceId) {
        this.imageResourceId = imageResourceId;
    }

    public void setText(String text) {
        this.text = text;
    }
}
