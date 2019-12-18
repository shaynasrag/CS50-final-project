package com.example.yswipe.Cards;

public class cards {
    private String userId;
    private String name;
    private String profilePicUrl;
    private String resCollege;
    private String year;

    public cards (String userId, String name, String profilePicUrl, String resCollege, String year) {

        this.userId = userId;
        this.name = name;
        this.profilePicUrl = profilePicUrl;
        this.resCollege = resCollege;
        this.year = year;

    }

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }
    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }


    public String getResCollege() {
        return resCollege;
    }
    public void setResCollege(String resCollege) {
        this.resCollege = resCollege;

    }

    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;}





}
