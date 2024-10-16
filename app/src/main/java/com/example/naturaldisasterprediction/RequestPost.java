package com.example.naturaldisasterprediction;

public class RequestPost {
    String UserName, UserEmail, UserContactNumber;

    public RequestPost(String userName, String userEmail, String userContactNumber) {
        UserName = userName;
        UserEmail = userEmail;
        UserContactNumber = userContactNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserContactNumber() {
        return UserContactNumber;
    }

    public void setUserContactNumber(String userContactNumber) {
        UserContactNumber = userContactNumber;
    }
}
