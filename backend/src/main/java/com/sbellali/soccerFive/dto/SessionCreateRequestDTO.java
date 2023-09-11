package com.sbellali.soccerFive.dto;

import java.time.LocalDateTime;

import com.sbellali.soccerFive.validator.SessionDuration.ValidSessionDuration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionCreateRequestDTO {

    private LocalDateTime startTime;

    @NotNull(message = "The duration is required.")
    @ValidSessionDuration
    private double duration;

    @NotNull(message = "The location is required.")
    private String location;

    @NotNull(message = "The max player is required.")
    @Max(12)
    private int maxPlayers;
    private double price;
    private String description;

}
