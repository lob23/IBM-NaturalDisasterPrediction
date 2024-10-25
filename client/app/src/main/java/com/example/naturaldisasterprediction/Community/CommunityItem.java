package com.example.naturaldisasterprediction.Community;

import android.content.Context;

import java.io.Serializable;

public class CommunityItem implements Serializable {
    private String blogId;
    private String senderId;
    private String regionId;
    private String blogContent;

    public CommunityItem(String blogId, String senderId, String regionId, String blogContent) {
        this.blogId = blogId;
        this.senderId = senderId;
        this.regionId = regionId;
        this.blogContent = blogContent;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }
}
