package com.omar.sams.Models;

public class EContentYearDataModel {

    String YearName;
    String SemesterUpName;
    String SemesterDownName;
    String SemesterUpLink;
    String SemesterDownLink;


    public EContentYearDataModel() {
    }

    public EContentYearDataModel(String yearName, String semesterUpName, String semesterDownName, String semesterUpLink, String semesterDownLink) {
        YearName = yearName;
        SemesterUpName = semesterUpName;
        SemesterDownName = semesterDownName;
        SemesterUpLink = semesterUpLink;
        SemesterDownLink = semesterDownLink;
    }

    public String getYearName() {
        return YearName;
    }

    public void setYearName(String yearName) {
        YearName = yearName;
    }

    public String getSemesterUpName() {
        return SemesterUpName;
    }

    public void setSemesterUpName(String semesterUpName) {
        SemesterUpName = semesterUpName;
    }

    public String getSemesterDownName() {
        return SemesterDownName;
    }

    public void setSemesterDownName(String semesterDownName) {
        SemesterDownName = semesterDownName;
    }

    public String getSemesterUpLink() {
        return SemesterUpLink;
    }

    public void setSemesterUpLink(String semesterUpLink) {
        SemesterUpLink = semesterUpLink;
    }

    public String getSemesterDownLink() {
        return SemesterDownLink;
    }

    public void setSemesterDownLink(String semesterDownLink) {
        SemesterDownLink = semesterDownLink;
    }
}
