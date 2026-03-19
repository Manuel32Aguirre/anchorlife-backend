package com.manuel.anchorlife.anchorlife.controllers;

import com.manuel.anchorlife.anchorlife.models.dto.request.VoiceProcessRequest;
import com.manuel.anchorlife.anchorlife.models.dto.response.VoiceProcessResponse;
import com.manuel.anchorlife.anchorlife.services.IEventService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    @Autowired
    private IEventService eventService;

    @PostMapping
    public ResponseEntity<VoiceProcessResponse> registerVoiceEvent(@RequestBody VoiceProcessRequest voiceProcessRequest){
        VoiceProcessResponse voiceProcessResponse = eventService.registerVoiceEvent(voiceProcessRequest);
        return ResponseEntity.status(HttpStatus.OK).body(voiceProcessResponse);
    }


}
