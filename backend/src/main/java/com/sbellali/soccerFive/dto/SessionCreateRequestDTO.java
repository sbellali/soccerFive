package com.sbellali.soccerFive.dto;

import java.time.LocalDateTime;

import com.sbellali.soccerFive.validator.SessionDuration.ValidSessionDuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionCreateRequestDTO {
    private LocalDateTime startTime;

    @ValidSessionDuration
    private double duration;
    private String location;
    private int maxParticipants;
    private double price;
    private String description;
}
