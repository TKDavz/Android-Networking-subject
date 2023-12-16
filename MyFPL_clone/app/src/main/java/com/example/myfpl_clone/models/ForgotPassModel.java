package com.example.myfpl_clone.models;

public class ForgotPassModel {
    private String email;

    public ForgotPassModel() {
    }

    public ForgotPassModel(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
