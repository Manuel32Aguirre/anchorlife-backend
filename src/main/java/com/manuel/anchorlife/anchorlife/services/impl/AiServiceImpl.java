package com.manuel.anchorlife.anchorlife.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manuel.anchorlife.anchorlife.models.entities.Event;
import com.manuel.anchorlife.anchorlife.models.entities.FixedEvent;
import com.manuel.anchorlife.anchorlife.services.IAiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AiServiceImpl implements IAiService {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    private static final String SYSTEM_PROMPT_TEMPLATE = """
            Actúas como un asistente de organización inteligente y empático. Tu objetivo es gestionar actividades y tareas para minimizar el estrés del usuario, encontrando el momento óptimo para cada una.
            
            CONTEXTO:
            - Fecha/Hora actual: %s (%s).
            - EVENTOS FIJOS (Inamovibles): %s. Estos son compromisos rígidos (escuela, trabajo, etc.). NO los puedes modificar.
            - EVENTOS FLEXIBLES (Agenda actual): %s. Son eventos que ya habías organizado. Puedes modificarlos o moverlos si es necesario para dar lugar a lo nuevo o mejorar el bienestar del usuario.
            
            REGLAS:
            1. Solo puedes RE-AGENDAR los "Eventos Flexibles".
            2. Si el usuario pide algo nuevo, búscale el mejor hueco.
            3. Analiza si es mejor mover lo que ya estaba agendado para que todo fluya mejor.
            4. Si decides que algo queda mejor mañana o después para no saturar al usuario, hazlo.
            
            RESPUESTA:
            DEBES devolver SIEMPRE una LISTA de objetos JSON (comenzando con [ y terminando con ]). 
            Incluso si solo hay un evento, envuélvelo en corchetes de lista.
        
            CONTENIDO DE LA LISTA:
            1. El NUEVO evento (id: null).
            2. Solo los eventos flexibles modificados (con su ID).
        
            FORMATO OBLIGATORIO:
            [
            {"id": null, "name": "...", "dateEvent": "yyyy-MM-dd'T'HH:mm:ss", "completed": false}
            ]
            """;

    public AiServiceImpl(WebClient.Builder webClientBuilder, @Value("${ollama.baseUrl}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public List<Event> parseText(String rawText, List<FixedEvent> fixedEventList, List<Event> currentEvents) {
        LocalDateTime now = LocalDateTime.now();
        String today = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String dayOfWeek = now.getDayOfWeek().toString();

        String formattedPrompt = String.format(SYSTEM_PROMPT_TEMPLATE,
                today, dayOfWeek, fixedEventList.toString(), currentEvents.toString(), today);

        Map<String, Object> requestBody = Map.of(
                "model", "qwen2.5:7b",
                "prompt", formattedPrompt + "\n\nSolicitud del usuario: " + rawText,
                "stream", false,
                "format", "json" // Qwen es excelente respetando este modo
        );

        Map<String, Object> ollamaResponse = this.webClient.post()
                .uri("/api/generate")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        String aiJsonResponse = (String) ollamaResponse.get("response");
        System.out.println("DEBUG - JSON de la IA: " + aiJsonResponse);

        try {
            // 1. Leemos el JSON como un árbol de nodos
            com.fasterxml.jackson.databind.JsonNode rootNode = objectMapper.readTree(aiJsonResponse.trim());
            com.fasterxml.jackson.databind.JsonNode eventsNode;

            // 2. ¿La IA envolvió todo en un objeto con la llave "events"?
            if (rootNode.has("events")) {
                eventsNode = rootNode.get("events");
            } else {
                // Si no tiene la llave "events", el nodo raíz es nuestro punto de partida
                eventsNode = rootNode;
            }

            // 3. Ahora que tenemos el nodo correcto, lo convertimos a nuestra lista
            if (eventsNode.isArray()) {
                // Caso: Es una lista [{}, {}]
                return objectMapper.convertValue(eventsNode, new com.fasterxml.jackson.core.type.TypeReference<List<Event>>() {});
            } else {
                // Caso: Es un objeto solo {}
                Event singleEvent = objectMapper.convertValue(eventsNode, Event.class);
                List<Event> wrapperList = new java.util.ArrayList<>();
                wrapperList.add(singleEvent);
                return wrapperList;
            }

        } catch (Exception e) {
            System.err.println("Error crítico al procesar el JSON: " + aiJsonResponse);
            e.printStackTrace(); // Para ver más detalle en consola
            throw new RuntimeException("Error al parsear el plan de la IA", e);
        }
    }
}