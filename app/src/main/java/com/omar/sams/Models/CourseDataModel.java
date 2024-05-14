package com.omar.sams.Models;

import java.util.ArrayList;

public class CourseDataModel {

    String semester;
    String name;
    String profName;
    String driveLink;
    String description;
    String notes;
    String profId;
    ArrayList<GroupDataModel> groups;

    public CourseDataModel() {
    }

    public CourseDataModel(String semester, String name, String profName, String driveLink, String description, String notes, String profId, ArrayList<GroupDataModel> groups) {
        this.semester = semester;
        this.name = name;
        this.profName = profName;
        this.driveLink = driveLink;
        this.description = description;
        this.notes = notes;
        this.profId = profId;
        this.groups = groups;
    }


    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    public String getDriveLink() {
        return driveLink;
    }

    public void setDriveLink(String driveLink) {
        this.driveLink = driveLink;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getProfId() {
        return profId;
    }

    public void setProfId(String profId) {
        this.profId = profId;
    }

    public ArrayList<GroupDataModel> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<GroupDataModel> groups) {
        this.groups = groups;
    }
}
