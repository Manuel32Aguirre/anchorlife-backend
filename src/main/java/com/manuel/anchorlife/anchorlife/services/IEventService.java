package com.manuel.anchorlife.anchorlife.services;

import com.manuel.anchorlife.anchorlife.models.dto.request.VoiceProcessRequest;
import com.manuel.anchorlife.anchorlife.models.dto.response.VoiceProcessResponse;

public interface IEventService {

    public VoiceProcessResponse registerVoiceEvent(VoiceProcessRequest voiceProcessRequest);
}
