package com.sbellali.soccerFive.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sbellali.soccerFive.validator.SessionDuration.ValidSessionDuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SessionDTO {

    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;

    @ValidSessionDuration
    private double duration;
    private String location;
    private int maxPlayers;
    private double price;
    private String description;
    private UserDTO organizer;
    private List<UserDTO> players;

}
