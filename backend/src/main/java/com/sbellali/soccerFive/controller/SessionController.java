package com.sbellali.soccerFive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sbellali.soccerFive.dto.SessionCreateRequestDTO;
import com.sbellali.soccerFive.exception.SessionNotFoundException;
import com.sbellali.soccerFive.model.User;
import com.sbellali.soccerFive.service.SessionService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@Validated
@RestController
@RequestMapping("session")
@CrossOrigin("*")
public class SessionController extends AbsractController {

    @Autowired
    private SessionService sessionService;

    @GetMapping(value = "/", params = "dateStart")
    public ResponseEntity<?> getSessionsByDate(
            @RequestParam(name = "dateStart") @Pattern(regexp = "^(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-\\d{4}$") String date) {

        ResponseEntity<?> response;
        try {
            response = this.successResponse(this.sessionService.getSessionsByDate(date));
        } catch (Exception e) {
            response = this.errorResponse();
        }
        return response;
    }

    @GetMapping(value = "/")
    public ResponseEntity<?> getAllSessions() {
        return this.successResponse(this.sessionService.getAllSessions());
    }

    @PostMapping(value = "/")
    public ResponseEntity<?> createSession(
            Authentication auth,
            @Valid @RequestBody SessionCreateRequestDTO sessionCreateRequest) {

        ResponseEntity<?> response;
        User user = (User) auth.getPrincipal();
        try {
            response = this.successResponse(
                    HttpStatus.CREATED,
                    sessionService.createSession(sessionCreateRequest, user));
        } catch (SessionNotFoundException e) {
            response = this.errorResponse(e.getHttpStatus(), e);
        } catch (Exception e) {
            response = this.errorResponse();
        }
        return response;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getSession(@PathVariable(name = "id") Integer id) {
        ResponseEntity<?> response;
        try {
            response = this.successResponse(this.sessionService.getSession(id));
        } catch (SessionNotFoundException e) {
            response = this.errorResponse(e.getHttpStatus(), e);
        } catch (Exception e) {
            response = this.errorResponse();
        }
        return response;
    }

    // TODO: Implementation
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> modifySession(@PathVariable(name = "id") Integer id) {
        return null;
    }

    // TODO: Implementation
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> getAllSessions(@PathVariable(name = "id") Integer id) {
        return null;
    }

}
