package com.manuel.anchorlife.anchorlife.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixedEventRegisterRequest {

    private Long userId;

    private String name;

    private DayOfWeek dayOfWeek;

    private LocalTime startTime;

    private LocalTime endTime;

}
