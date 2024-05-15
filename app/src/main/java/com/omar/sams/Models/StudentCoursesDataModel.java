package com.omar.sams.Models;

import java.util.ArrayList;

public class StudentCoursesDataModel {

    String courseId;
    String midtermGrade;
    String finalGrade;
    String classwork;
    String total;
    String gradeAlpha;
    ArrayList<AttendanceDataModel> attendance;


    public StudentCoursesDataModel() {
    }

    public StudentCoursesDataModel(String courseId, String midtermGrade, String finalGrade, String classwork, String total, String gradeAlpha, ArrayList<AttendanceDataModel> attendance) {
        this.courseId = courseId;
        this.midtermGrade = midtermGrade;
        this.finalGrade = finalGrade;
        this.classwork = classwork;
        this.total = total;
        this.gradeAlpha = gradeAlpha;
        this.attendance = attendance;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getMidtermGrade() {
        return midtermGrade;
    }

    public void setMidtermGrade(String midtermGrade) {
        this.midtermGrade = midtermGrade;
    }

    public String getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(String finalGrade) {
        this.finalGrade = finalGrade;
    }

    public String getClasswork() {
        return classwork;
    }

    public void setClasswork(String classwork) {
        this.classwork = classwork;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getGradeAlpha() {
        return gradeAlpha;
    }

    public void setGradeAlpha(String gradeAlpha) {
        this.gradeAlpha = gradeAlpha;
    }

    public ArrayList<AttendanceDataModel> getAttendance() {
        return attendance;
    }

    public void setAttendance(ArrayList<AttendanceDataModel> attendance) {
        this.attendance = attendance;
    }
}
