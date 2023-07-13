package com.example.uas_pbo_2;

public class User {

    private String username;
    private String password;
    private int id_user;

    public User(){

    }

    public User(String username, String password, int id_user) {
        this.username = username;
        this.password = password;
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId_user() {
        return String.valueOf(Integer.parseInt(String.valueOf(id_user)));
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
}
