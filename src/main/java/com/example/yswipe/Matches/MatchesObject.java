package com.example.yswipe.Matches;

public class MatchesObject {
    private String userId;
    private String name;
    private String profilePicUrl;
    public MatchesObject (String userId, String name, String profilePicUrl) {
        this.userId = userId;
        this.name = name;
        this.profilePicUrl = profilePicUrl;
 ;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getProfilePicUrl(){
        return profilePicUrl;
    }
    public void setProfilePicUrl(String profilePicUrl){
        this.profilePicUrl = profilePicUrl;

    }
}
