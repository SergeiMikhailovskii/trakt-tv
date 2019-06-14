package com.mikhailovskii.trakttv.data.entity;

import androidx.annotation.NonNull;

import javax.annotation.Nullable;

public class User {

    private String email;
    private String password;
    private String token;
    private String username;
    private String id;
    private String avatar;

    public User(@Nullable String email, @NonNull String password, @Nullable String token, @NonNull String username, @Nullable String id, @Nullable String avatar) {
        this.email = email;
        this.password = password;
        this.token = token;
        this.username = username;
        this.id = id;
        this.avatar = avatar;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @Nullable
    public String getToken() {
        return token;
    }

    public void setToken(@NonNull String token) {
        this.token = token;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @Nullable
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @Nullable
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(@NonNull String avatar) {
        this.avatar = avatar;
    }
}
