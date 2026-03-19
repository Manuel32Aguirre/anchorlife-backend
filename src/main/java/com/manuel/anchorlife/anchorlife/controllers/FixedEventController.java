package com.manuel.anchorlife.anchorlife.controllers;

import com.manuel.anchorlife.anchorlife.models.dto.request.FixedEventRegisterRequest;
import com.manuel.anchorlife.anchorlife.models.dto.response.FixedEventRegisterResponse;
import com.manuel.anchorlife.anchorlife.services.IFixedEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/fixed-events")
public class FixedEventController {


    @Autowired
    private IFixedEventService fixedEventService;

    @PostMapping
    public ResponseEntity<FixedEventRegisterResponse> register(@RequestBody FixedEventRegisterRequest fixedEventRegisterRequest){
        FixedEventRegisterResponse response = fixedEventService.register(fixedEventRegisterRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
