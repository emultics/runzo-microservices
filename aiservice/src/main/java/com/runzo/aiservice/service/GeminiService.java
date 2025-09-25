package com.runzo.aiservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Service
public class GeminiService {

    private final WebClient geminiClient;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;


    public GeminiService(WebClient geminiClient){
        this.geminiClient = geminiClient;
    }

    public String getRecommendations(String details){
        Map<String, Object> map = Map.of(
                "contents",
                new Object[]{
                        Map.of("parts", new Object[]{
                                Map.of("text", details)
                        })
                }

        );


        return geminiClient.post()
                .uri(geminiApiUrl).header("Content-Type", "application/json")
                .header("X-goog-api-key", geminiApiKey)
                .bodyValue(map)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
