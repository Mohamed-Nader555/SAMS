package com.omar.sams.Models;

public class ProfessorDataModel {

    String profId;
    String name;
    String email;
    String password;
    String courseId;

    public ProfessorDataModel() {
    }

    public ProfessorDataModel(String profId, String name, String email, String password, String courseId) {
        this.profId = profId;
        this.name = name;
        this.email = email;
        this.password = password;
         this.courseId = courseId;
    }

    public String getProfId() {
        return profId;
    }

    public void setProfId(String profId) {
        this.profId = profId;
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

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}
