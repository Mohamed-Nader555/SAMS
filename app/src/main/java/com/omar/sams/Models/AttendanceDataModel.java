package com.omar.sams.Models;

import java.util.Date;

public class AttendanceDataModel {

    Date date;
    Boolean isPresent;

    public AttendanceDataModel() {
    }

    public AttendanceDataModel(Date date, Boolean isPresent) {
        this.date = date;
        this.isPresent = isPresent;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
        isPresent = present;
    }
}
