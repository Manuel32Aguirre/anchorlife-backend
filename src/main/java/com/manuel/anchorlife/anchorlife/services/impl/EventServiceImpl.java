package com.manuel.anchorlife.anchorlife.services.impl;

import com.manuel.anchorlife.anchorlife.models.entities.Event;
import com.manuel.anchorlife.anchorlife.models.entities.FixedEvent;
import com.manuel.anchorlife.anchorlife.models.entities.User;
import com.manuel.anchorlife.anchorlife.models.dto.request.VoiceProcessRequest;
import com.manuel.anchorlife.anchorlife.models.dto.response.VoiceProcessResponse;
import com.manuel.anchorlife.anchorlife.models.mappers.EventMapper;
import com.manuel.anchorlife.anchorlife.repositories.FixedEventRepository;
import com.manuel.anchorlife.anchorlife.repositories.UserRepository;
import com.manuel.anchorlife.anchorlife.services.IAiService;
import com.manuel.anchorlife.anchorlife.services.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements IEventService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IAiService aiService;

    @Autowired
    private EventMapper eventMapper;

    @Autowired


    private FixedEventRepository fixedEventRepository;

    @Override
    public VoiceProcessResponse registerVoiceEvent(VoiceProcessRequest voiceProcessRequest) {
        User user = userRepository.findById(voiceProcessRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<FixedEvent> fixedEventsDb = user.getFixedEvents();
        Event aiParseText = aiService.parseText(voiceProcessRequest.getRawText(), fixedEventsDb);

        user.getEvents().add(aiParseText);
        userRepository.save(user);

        return eventMapper.toResponse(aiParseText);
    }
}
