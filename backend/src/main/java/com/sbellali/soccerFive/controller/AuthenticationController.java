package com.sbellali.soccerFive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbellali.soccerFive.dto.LoginDTO;
import com.sbellali.soccerFive.dto.LoginResponseDTO;
import com.sbellali.soccerFive.dto.RegistrationDTO;
import com.sbellali.soccerFive.dto.UserDTO;
import com.sbellali.soccerFive.exception.UserAlreadyExistsException;
import com.sbellali.soccerFive.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController extends AbsractController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO registrationDTO) {
        ResponseEntity<?> response;
        try {
            UserDTO user = authenticationService.registerUser(
                    registrationDTO.getUsername(),
                    registrationDTO.getEmail(),
                    registrationDTO.getPassword());
            response = this.successResponse(HttpStatus.CREATED, user);

        } catch (UserAlreadyExistsException e) {
            response = this.errorResponse(HttpStatus.CONFLICT, e);
        } catch (Exception e) {
            response = this.errorResponse();
        }
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDTO) {
        ResponseEntity<?> response;
        try {
            LoginResponseDTO loginResponse = authenticationService.loginUser(loginDTO.getUsername(),
                    loginDTO.getPassword());
            response = this.successResponse(loginResponse);

        } catch (BadCredentialsException | DisabledException e) {
            response = this.errorResponse(HttpStatus.FORBIDDEN, e);
        } catch (LockedException e) {
            response = this.errorResponse(HttpStatus.LOCKED, e);
        } catch (Exception e) {
            response = this.errorResponse();
        }
        return response;
    }

}
