package com.example.kalidigitalemployee.retrofit;

public class User {
    private String id;
    private String email;
    private String username;

    public User(String id, String email, String username, String date_joined) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.date_joined = date_joined;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(String date_joined) {
        this.date_joined = date_joined;
    }

    private String date_joined;


}
