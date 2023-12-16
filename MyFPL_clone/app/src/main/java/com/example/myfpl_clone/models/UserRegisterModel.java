package com.example.myfpl_clone.models;

public class UserRegisterModel extends UserModel{
    private String confirm_password;


    public UserRegisterModel() {
    }

    public UserRegisterModel(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public UserRegisterModel(int id, String email, String password, String name, String phone, String address, String created_at, String role, String confirm_password) {
        super(id, email, password, name, phone, address, created_at, role);
        this.confirm_password = confirm_password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }
}
