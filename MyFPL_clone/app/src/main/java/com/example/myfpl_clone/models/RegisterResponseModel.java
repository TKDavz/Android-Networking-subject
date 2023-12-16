package com.example.myfpl_clone.models;

public class RegisterResponseModel {
    private Boolean status;
    private UserModel user;
    private String message;

    public RegisterResponseModel() {
    }

    public RegisterResponseModel(Boolean status, UserModel user, String message) {
        this.status = status;
        this.user = user;
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
