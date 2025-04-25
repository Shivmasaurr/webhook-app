package com.example.webhookapp.model;

import java.util.List;

public class FollowRequest {
    private int n;
    private int findId;
    private List<User> users;

    // Getters and Setters
    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getFindId() {
        return findId;
    }

    public void setFindId(int findId) {
        this.findId = findId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
} 