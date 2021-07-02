package com.example.JWT.models;

public class ChangePassword {
    private String username;
    private String old_password;
    private String new_password;

    public ChangePassword(){

    }

    public String getNew_password() {
        return new_password;
    }

    public String getOld_password() {
        return old_password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }
}
