package com.manuel.anchorlife.anchorlife.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoiceProcessRequest {
    private Long userId;
    private String rawText;
}
