package com.mikhailovskii.trakttv.data.model;

public class User {

    private String email;
    private String password;
    private String token;
    private String username;

    // todo add annotations
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    void setUsername(String username) {
        this.username = username;
    }

}