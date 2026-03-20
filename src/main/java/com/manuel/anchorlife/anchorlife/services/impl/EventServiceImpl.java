package com.manuel.anchorlife.anchorlife.services.impl;

import com.manuel.anchorlife.anchorlife.models.entities.Event;
import com.manuel.anchorlife.anchorlife.models.entities.User;
import com.manuel.anchorlife.anchorlife.models.dto.request.VoiceProcessRequest;
import com.manuel.anchorlife.anchorlife.models.dto.response.VoiceProcessResponse;
import com.manuel.anchorlife.anchorlife.models.mappers.EventMapper;
import com.manuel.anchorlife.anchorlife.repositories.UserRepository;
import com.manuel.anchorlife.anchorlife.services.IAiService;
import com.manuel.anchorlife.anchorlife.services.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements IEventService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IAiService aiService;

    @Autowired
    private EventMapper eventMapper;

    @Override
    @Transactional
    public VoiceProcessResponse registerVoiceEvent(VoiceProcessRequest voiceProcessRequest) {
        User user = userRepository.findById(voiceProcessRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Event> pendingEvents = new ArrayList<>();

        for (Event e : user.getEvents()) {
            if (!e.isCompleted()) {
                pendingEvents.add(e);
            }
        }

        List<Event> aiPlan = aiService.parseText(
                voiceProcessRequest.getRawText(),
                user.getFixedEvents(),
                pendingEvents
        );

        for (Event plannedEvent : aiPlan) {
            if (plannedEvent.getId() == null) {
                // Es un evento nuevo
                user.getEvents().add(plannedEvent);
            } else {
                // Es un ajuste de uno que ya existía
                for (Event existingEvent : user.getEvents()) {
                    if (existingEvent.getId() != null && existingEvent.getId().equals(plannedEvent.getId())) {
                        existingEvent.setName(plannedEvent.getName());
                        existingEvent.setDateEvent(plannedEvent.getDateEvent());
                        existingEvent.setCompleted(plannedEvent.isCompleted());
                    }
                }
            }
        }

        userRepository.save(user);

        return eventMapper.toResponse(aiPlan.get(0));
    }
}