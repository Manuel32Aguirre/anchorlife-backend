package com.manuel.anchorlife.anchorlife.services.impl;

import com.manuel.anchorlife.anchorlife.models.entities.FixedEvent;
import com.manuel.anchorlife.anchorlife.models.entities.User;
import com.manuel.anchorlife.anchorlife.models.dto.request.FixedEventRegisterRequest;
import com.manuel.anchorlife.anchorlife.models.dto.response.FixedEventRegisterResponse;
import com.manuel.anchorlife.anchorlife.models.mappers.FixedEventMapper;
import com.manuel.anchorlife.anchorlife.repositories.FixedEventRepository;
import com.manuel.anchorlife.anchorlife.repositories.UserRepository;
import com.manuel.anchorlife.anchorlife.services.IFixedEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FixedEventServiceImpl implements IFixedEventService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FixedEventRepository fixedEventRepository;

    @Autowired
    private FixedEventMapper fixedEventMapper;

    @Override
    public FixedEventRegisterResponse register(FixedEventRegisterRequest fixedEventRegisterRequest) {
        User user = userRepository.findById(fixedEventRegisterRequest.getUserId()).orElseThrow(
                () -> new RuntimeException("El usuario con id" +  fixedEventRegisterRequest.getUserId() + " no existe")
        );
        FixedEvent fixedEvent = fixedEventMapper.toEntity(fixedEventRegisterRequest);
        List<FixedEvent> fixedEventList = user.getFixedEvents();
        fixedEventList.add(fixedEvent);
        user.setFixedEvents(fixedEventList);
        fixedEventRepository.save(fixedEvent);

        return fixedEventMapper.toResponse(fixedEvent);

    }
}
