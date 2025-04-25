package com.example.webhookapp.service;

import com.example.webhookapp.model.User;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FollowService {
    
    public List<Integer> findNthLevelFollowers(int findId, int n, List<User> users) {
        if (n <= 0) {
            return Collections.emptyList();
        }

        // Create a map for quick user lookup
        Map<Integer, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, user -> user));

        // Initialize the queue with the starting user's direct followers
        Queue<Integer> queue = new LinkedList<>();
        User startUser = userMap.get(findId);
        if (startUser == null) {
            return Collections.emptyList();
        }
        queue.addAll(startUser.getFollows());

        // BFS to find nth level followers
        int currentLevel = 1;
        while (!queue.isEmpty() && currentLevel < n) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int currentUserId = queue.poll();
                User currentUser = userMap.get(currentUserId);
                if (currentUser != null) {
                    queue.addAll(currentUser.getFollows());
                }
            }
            currentLevel++;
        }

        // If we've reached the nth level, return the current queue contents
        if (currentLevel == n) {
            return new ArrayList<>(queue);
        }

        return Collections.emptyList();
    }
} 