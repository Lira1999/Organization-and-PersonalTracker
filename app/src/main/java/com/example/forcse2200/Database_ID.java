package com.example.forcse2200;

public class Database_ID {
    String lastID;
    public Database_ID(){}

    public Database_ID(String lastID) {
        this.lastID = lastID;
    }

    public String getLastID() {
        return lastID;
    }

    public void setLastID(String lastID) {
        this.lastID = lastID;
    }
}
