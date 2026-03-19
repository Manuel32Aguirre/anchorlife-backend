package com.manuel.anchorlife.anchorlife.models.mappers;

import com.manuel.anchorlife.anchorlife.models.entities.Event;
import com.manuel.anchorlife.anchorlife.models.dto.response.VoiceProcessResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {
    //Convertimos de entidad Evento a un response
    public VoiceProcessResponse toResponse(Event event);
}
