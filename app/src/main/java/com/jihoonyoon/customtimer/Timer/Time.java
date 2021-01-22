package com.jihoonyoon.customtimer.Timer;

import io.realm.RealmObject;

public class Time extends RealmObject {
    private int time;
    private String name;

    public Time(){

    }

    public Time(int time, String name) {
        this.time = time;
        this.name = name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
