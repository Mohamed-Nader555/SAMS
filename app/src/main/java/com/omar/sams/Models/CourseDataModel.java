package com.omar.sams.Models;

import java.util.ArrayList;

public class CourseDataModel {

    String semester;
    String name;
    String prof;
    String driveLink;
    String description;
    String notes;
    ArrayList<GroupDataModel> groups;

    public CourseDataModel() {
    }

    public CourseDataModel(String semester, String name, String prof, String driveLink, String description, String notes, ArrayList<GroupDataModel> groups) {
        this.semester = semester;
        this.name = name;
        this.prof = prof;
        this.driveLink = driveLink;
        this.description = description;
        this.notes = notes;
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

    public String getProf() {
        return prof;
    }

    public void setProf(String prof) {
        this.prof = prof;
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

    public ArrayList<GroupDataModel> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<GroupDataModel> groups) {
        this.groups = groups;
    }
}
