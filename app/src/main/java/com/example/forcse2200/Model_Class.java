package com.example.forcse2200;

public class Model_Class {

    String Title, Description, id;

    public Model_Class( String id, String title, String description) {
        Title = title;
        Description = description;
        this.id = id;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public String getId() {
        return id;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setId(String id) {
        this.id = id;
    }
}
