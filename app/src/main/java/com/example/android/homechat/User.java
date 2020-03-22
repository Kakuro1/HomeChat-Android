package com.example.android.homechat;

public class User {

    private String userId;
    private String username;
    private String homeGroupId;

    public User() {
    }

    public User(String userId, String username, String homeGroupId) {
        this.userId = userId;
        this.username = username;
        this.homeGroupId = homeGroupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHomeGroupId() {
        return homeGroupId;
    }

    public void setHomeGroupId(String homeGroupId) {
        this.homeGroupId = homeGroupId;
    }
}
