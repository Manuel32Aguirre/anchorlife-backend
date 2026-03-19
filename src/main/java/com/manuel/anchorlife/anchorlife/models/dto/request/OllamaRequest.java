package com.manuel.anchorlife.anchorlife.models.dto.request;

import lombok.Data;

@Data
public class OllamaRequest {
    private String model = "llama3.1";
    private String prompt;
    private boolean stream = false;
}