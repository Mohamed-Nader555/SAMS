package com.omar.sams.Models;

import java.util.ArrayList;
import java.util.Date;

public class GroupDataModel {

    String name;
    String room;
    String time;
    String day;
    ArrayList<Date> lectureDates;

    public GroupDataModel() {
    }

    public GroupDataModel(String name, String room, String time, String day, ArrayList<Date> lectureDates) {
        this.name = name;
        this.room = room;
        this.time = time;
        this.day = day;
        this.lectureDates = lectureDates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ArrayList<Date> getLectureDates() {
        return lectureDates;
    }

    public void setLectureDates(ArrayList<Date> lectureDates) {
        this.lectureDates = lectureDates;
    }
}
