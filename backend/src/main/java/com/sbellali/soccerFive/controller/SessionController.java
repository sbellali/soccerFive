package com.sbellali.soccerFive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sbellali.soccerFive.service.SessionService;

import jakarta.validation.constraints.Pattern;

@Validated
@RestController
@RequestMapping("session")
@CrossOrigin("*")
public class SessionController extends AbsractController {

    @Autowired
    private SessionService sessionService;

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllSessions() {
        return this.successResponse(this.sessionService.getAllSessions());
    }

    @GetMapping(value = "/date/{date}")
    public ResponseEntity<?> getSessionsByDate(
            @PathVariable(name = "date") @Pattern(regexp = "^(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-\\d{4}$") String date) {
        ResponseEntity<?> response;
        try {
            response = this.successResponse(this.sessionService.getSessionsByDate(date));
        } catch (Exception e) {
            response = this.errorResponse();
        }
        return response;
    }

}
