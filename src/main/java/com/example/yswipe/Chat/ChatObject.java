package com.example.yswipe.Chat;

public class ChatObject {
    private String message;
    private Boolean currentUser;

    public ChatObject(String message, Boolean currentUser) {
        this.message = message;
        this.currentUser = currentUser;
    }
    public String getMessage(){
        return message;
    }
    public void setMessage(String message){
        this.message = message;
    }

    public Boolean getcurrentUser(){
        return currentUser;
    }
    public void setcurrentUser(Boolean currentUser){
        this.currentUser = currentUser;
    }

}
