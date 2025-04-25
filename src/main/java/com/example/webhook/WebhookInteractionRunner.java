package com.example.webhookapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class WebhookInteractionRunner implements CommandLineRunner {

    private final RestTemplate restTemplate;

    public WebhookInteractionRunner(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void run(String... args) {
        callGenerateWebhookAndProcess();
    }

    private void callGenerateWebhookAndProcess() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "John Doe");
        requestBody.put("regNo", "REG12347");
        requestBody.put("email", "john@example.com");

        ResponseEntity<WebhookResponse> responseEntity = restTemplate.postForEntity(
            "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook",
            requestBody,
            WebhookResponse.class
        );

        WebhookResponse response = responseEntity.getBody();
        if (response != null) {
            processWebhookResponse(response);
        }
    }

    private void processWebhookResponse(WebhookResponse response) {
        String webhook = response.getWebhook();
        String accessToken = response.getAccessToken();
        List<User> users = response.getData().getUsers();

        String regNo = "REG12347";
        int lastTwoDigits = Integer.parseInt(regNo.substring(regNo.length() - 2));
        boolean isOdd = lastTwoDigits % 2 != 0;

        List<List<Integer>> outcome = isOdd ? solveQuestionForOddRegNo(users) : solveQuestionForEvenRegNo(users);
        sendResultToWebhook(webhook, accessToken, outcome);
    }

    private List<List<Integer>> solveQuestionForOddRegNo(List<User> users) {
        List<List<Integer>> mutualFollows = new ArrayList<>();
        for (User u1 : users) {
            for (User u2 : users) {
                if (u1.getId() < u2.getId() && 
                    u1.getFollows().contains(u2.getId()) && 
                    u2.getFollows().contains(u1.getId())) {
                    mutualFollows.add(Arrays.asList(u1.getId(), u2.getId()));
                }
            }
        }
        return mutualFollows;
    }

    private List<List<Integer>> solveQuestionForEvenRegNo(List<User> users) {
        // Placeholder: replace with actual logic for even regNo problem
        return new ArrayList<>();
    }

    private void sendResultToWebhook(String webhook, String accessToken, List<List<Integer>> outcome) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("outcome", outcome);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", accessToken);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        sendWithRetry(webhook, entity);
    }

    private void sendWithRetry(String url, HttpEntity<?> entity) {
        int maxRetries = 4;
        int attempts = 0;
        boolean success = false;

        while (!success && attempts < maxRetries) {
            try {
                ResponseEntity<String> response = restTemplate.exchange(
                    url, HttpMethod.POST, entity, String.class);
                if (response.getStatusCode().is2xxSuccessful()) {
                    success = true;
                    System.out.println("✅ Successfully sent result.");
                } else {
                    attempts++;
                    System.out.println("⚠️ Failed attempt " + attempts + ". Status: " + response.getStatusCode());
                }
            } catch (Exception e) {
                attempts++;
                System.out.println("❌ Error on attempt " + attempts + ": " + e.getMessage());
            }
        }

        if (!success) {
            System.out.println("💥 All attempts failed. Giving up.");
        }
    }
}
