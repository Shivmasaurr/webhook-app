package com.example.webhookapp.model;

import java.util.List;

public class FollowResponse {
    private String regNo;
    private List<Integer> outcome;

    public FollowResponse(String regNo, List<Integer> outcome) {
        this.regNo = regNo;
        this.outcome = outcome;
    }

    // Getters and Setters
    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public List<Integer> getOutcome() {
        return outcome;
    }

    public void setOutcome(List<Integer> outcome) {
        this.outcome = outcome;
    }
} 