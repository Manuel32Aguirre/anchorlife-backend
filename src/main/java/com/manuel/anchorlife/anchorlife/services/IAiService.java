package com.manuel.anchorlife.anchorlife.services;

import com.manuel.anchorlife.anchorlife.models.entities.Event;
import com.manuel.anchorlife.anchorlife.models.entities.FixedEvent;

import java.util.List;

public interface IAiService {
    Event parseText(String rawText, List<FixedEvent> fixedEventList);
}
