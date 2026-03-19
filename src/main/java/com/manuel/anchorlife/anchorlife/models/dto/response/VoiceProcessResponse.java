package com.manuel.anchorlife.anchorlife.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoiceProcessResponse {

    private Long id;

    private String name;

    private Date dateEvent;

    private boolean completed;
}
