package com.manuel.anchorlife.anchorlife.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manuel.anchorlife.anchorlife.models.entities.Event;
import com.manuel.anchorlife.anchorlife.models.entities.FixedEvent;
import com.manuel.anchorlife.anchorlife.services.IAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class AiServiceImpl implements IAiService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    private final String systemPrompt;

    public AiServiceImpl(
            WebClient.Builder webClientBuilder,
            @Value("${ollama.baseUrl}") String baseUrl,
            @Value("${ollama.system.prompt}") String systemPrompt
    ) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.systemPrompt = systemPrompt;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Event parseText(String rawText, List<FixedEvent> fixedEventList) {
        LocalDateTime now = LocalDateTime.now();
        String today = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String dayOfWeek = now.getDayOfWeek().toString();

        String formattedPrompt = String.format(this.systemPrompt, today, dayOfWeek, fixedEventList.toString(), today);

        Map<String, Object> requestBody = Map.of(
                "model", "llama3.2",
                "prompt", formattedPrompt + "\n\nMensaje del usuario: " + rawText,
                "stream", false,
                "format", "json"
        );

        Map<String, Object> ollamaResponse = this.webClient.post()
                .uri("/api/generate")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        String aiJsonResponse = (String) ollamaResponse.get("response");
        try {
            return objectMapper.readValue(aiJsonResponse.trim(), Event.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el criterio de la IA", e);
        }
    }
}