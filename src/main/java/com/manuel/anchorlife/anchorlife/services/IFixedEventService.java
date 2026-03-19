package com.manuel.anchorlife.anchorlife.services;

import com.manuel.anchorlife.anchorlife.models.dto.request.FixedEventRegisterRequest;
import com.manuel.anchorlife.anchorlife.models.dto.response.FixedEventRegisterResponse;

public interface IFixedEventService {
    FixedEventRegisterResponse register(FixedEventRegisterRequest fixedEventRegisterRequest);
}
