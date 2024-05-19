package com.omar.sams.Models;

public class ClubsDataModel {

    String clubName;
    String clubLink;
    String clubImg;


    public ClubsDataModel() {
    }


    public ClubsDataModel(String clubName, String clubLink, String clubImg) {
        this.clubName = clubName;
        this.clubLink = clubLink;
        this.clubImg = clubImg;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getClubLink() {
        return clubLink;
    }

    public void setClubLink(String clubLink) {
        this.clubLink = clubLink;
    }

    public String getClubImg() {
        return clubImg;
    }

    public void setClubImg(String clubImg) {
        this.clubImg = clubImg;
    }
}
