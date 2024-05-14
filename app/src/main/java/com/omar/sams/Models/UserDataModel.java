package com.omar.sams.Models;

public class UserDataModel {

    //we will add a boolean to check if the user is prof or not and this boolean by default will be false  and we can edit it manually from the database in fire base
    String userId;
    String fullName;
    String email;
    String phoneNumber;
    String password;
    String profileImage;
    String semester;
    String group;
    Boolean isProf;
    ProfessorDataModel professor;
    StudentDataModel student;

    public UserDataModel() {
    }

    public UserDataModel(String userId, String fullName, String email, String phoneNumber, String password, String profileImage, String semester, String group, Boolean isProf, ProfessorDataModel professor, StudentDataModel student) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.profileImage = profileImage;
        this.semester = semester;
        this.group = group;
        this.isProf = isProf;
        this.professor = professor;
        this.student = student;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Boolean getProf() {
        return isProf;
    }

    public void setProf(Boolean prof) {
        isProf = prof;
    }

    public ProfessorDataModel getProfessor() {
        return professor;
    }

    public void setProfessor(ProfessorDataModel professor) {
        this.professor = professor;
    }

    public StudentDataModel getStudent() {
        return student;
    }

    public void setStudent(StudentDataModel student) {
        this.student = student;
    }
}
