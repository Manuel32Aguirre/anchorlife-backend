package com.manuel.anchorlife.anchorlife.models.mappers;

import com.manuel.anchorlife.anchorlife.models.entities.FixedEvent;
import com.manuel.anchorlife.anchorlife.models.dto.request.FixedEventRegisterRequest;
import com.manuel.anchorlife.anchorlife.models.dto.response.FixedEventRegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FixedEventMapper {
    FixedEvent toEntity(FixedEventRegisterRequest fixedEventRegisterRequest);
    FixedEventRegisterResponse toResponse(FixedEvent fixedEvent);
}
