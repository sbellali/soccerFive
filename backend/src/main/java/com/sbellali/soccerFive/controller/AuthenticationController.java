package com.sbellali.soccerFive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbellali.soccerFive.dto.LoginDTO;
import com.sbellali.soccerFive.dto.RegistrationDTO;
import com.sbellali.soccerFive.dto.UserDTO;
import com.sbellali.soccerFive.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public UserDTO registerUser(@RequestBody RegistrationDTO registrationDTO) {
        return authenticationService.registerUser(
                registrationDTO.getUsername(),
                registrationDTO.getEmail(),
                registrationDTO.getPassword());
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        return authenticationService.LoginUser(loginDTO.getUsername(), loginDTO.getPassword());
    }

}
