package com.example.forcse2200;

public class PROFILE_FORM {

    String name, email, password, gender, birth, imageURL, events, userID;

    //a default constructor for retrieve nor app will crash
    public PROFILE_FORM() {
    }

    public PROFILE_FORM(String name, String email, String password, String gender, String birth, String imageURL, String events, String userID) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.birth = birth;
        this.imageURL = imageURL;
        this.events = events;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }



    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}