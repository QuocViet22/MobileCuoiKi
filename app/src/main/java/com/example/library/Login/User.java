package com.example.library.Login;

public class User {

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USERNAME = "userName";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_IMAGE = "image";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String userName;
    public String password;
    public byte[] image;

    public User(String user, String password, byte[] image) {
        this.userName = user;
        this.password = password;
        this.image = image;
    }

    public User(){

    }
}
