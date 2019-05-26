package com.mikhailovskii.trakttv.data.model;

import android.support.annotation.NonNull;

public class User {

    private String email;
    private String password;
    private String token;
    private String username;
    private String id;

    public User(String email, String password, String token, String username, String id) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.username = username;
        this.id = id;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
