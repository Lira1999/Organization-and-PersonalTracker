package com.example.forcse2200;

public class User {
    String Events, Switch, Share_to, Share_from;
    public User()
    {

    }

    public User(String events, String aSwitch, String share_to, String share_from) {
        this.Events = events;
        this.Switch = aSwitch;
        this.Share_to = share_to;
        this.Share_from = share_from;
    }

    public String getEvents() {
        return Events;
    }

    public void setEvents(String events) {
        Events = events;
    }

    public String getSwitch() {
        return Switch;
    }

    public void setSwitch(String aSwitch) {
        Switch = aSwitch;
    }

    public String getShare_to() {
        return Share_to;
    }

    public void setShare_to(String share_to) {
        Share_to = share_to;
    }

    public String getShare_from() {
        return Share_from;
    }

    public void setShare_from(String share_from) {
        Share_from = share_from;
    }
}
