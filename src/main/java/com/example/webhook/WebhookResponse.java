package com.example.webhookapp;

public class WebhookResponse {
    private String webhook;
    private String accessToken;
    private WebhookData data;

    // Getters and Setters
    public String getWebhook() { return webhook; }
    public void setWebhook(String webhook) { this.webhook = webhook; }

    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public WebhookData getData() { return data; }
    public void setData(WebhookData data) { this.data = data; }
}
