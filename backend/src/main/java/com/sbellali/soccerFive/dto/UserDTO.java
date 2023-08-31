package com.sbellali.soccerFive.dto;

import java.util.Set;

import com.sbellali.soccerFive.model.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private int id;
    private String username;
    private String email;
    private Set<Role> authorities;
    private String photoUrl;
}
