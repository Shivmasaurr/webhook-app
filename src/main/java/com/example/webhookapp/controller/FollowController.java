package com.example.webhookapp.controller;

import com.example.webhookapp.model.FollowRequest;
import com.example.webhookapp.model.FollowResponse;
import com.example.webhookapp.service.FollowService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FollowController {

    private final FollowService followService;

    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    @PostMapping("/find-followers")
    public FollowResponse findNthLevelFollowers(@RequestBody FollowRequest request) {
        List<Integer> followers = followService.findNthLevelFollowers(
            request.getFindId(),
            request.getN(),
            request.getUsers()
        );
        
        return new FollowResponse("REG12347", followers);
    }
} 