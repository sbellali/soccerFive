package com.sbellali.soccerFive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistrationDTO {
    private String username;
    private String email;
    private String password;
}
