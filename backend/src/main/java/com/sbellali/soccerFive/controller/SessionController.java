package com.sbellali.soccerFive.controller;

import java.util.List;

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

import com.sbellali.soccerFive.dto.SessionRequestDTO;
import com.sbellali.soccerFive.exception.MaxPlayersReachedException;
import com.sbellali.soccerFive.exception.SessionNotFoundException;
import com.sbellali.soccerFive.exception.UnauthorizedActionException;
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

    @GetMapping(value = "", params = "dateStart")
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

    @GetMapping(value = "")
    public ResponseEntity<?> getAllSessions() {
        return this.successResponse(this.sessionService.getAllSessions());
    }

    @PostMapping(value = "")
    public ResponseEntity<?> createSession(
            Authentication auth,
            @Valid @RequestBody SessionRequestDTO sessionCreateRequest) {
        ResponseEntity<?> response;
        User user = (User) auth.getPrincipal();
        try {
            response = this.successResponse(
                    HttpStatus.CREATED,
                    sessionService.createSession(sessionCreateRequest, user));
        } catch (SessionNotFoundException e) {
            response = this.errorResponse(e);
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
            response = this.errorResponse(e);
        } catch (Exception e) {
            response = this.errorResponse();
        }
        return response;
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> modifySession(
            Authentication auth,
            @Valid @RequestBody SessionRequestDTO sessionCreateRequest,
            @PathVariable(name = "id") Integer id) {
        ResponseEntity<?> response;
        User user = (User) auth.getPrincipal();
        try {
            response = this.successResponse(this.sessionService.modifySession(id, sessionCreateRequest, user));
        } catch (SessionNotFoundException | UnauthorizedActionException e) {
            response = this.errorResponse(e);
        } catch (Exception e) {
            response = this.errorResponse();
        }
        return response;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteSession(Authentication auth, @PathVariable(name = "id") Integer id) {
        ResponseEntity<?> response;
        User user = (User) auth.getPrincipal();
        try {
            this.sessionService.deleteSession(id, user);
            response = this.successResponse("Session successfully deleted.");
        } catch (SessionNotFoundException | UnauthorizedActionException e) {
            response = this.errorResponse(e);
        } catch (Exception e) {
            response = this.errorResponse();
        }

        return response;
    }

    @PostMapping(value = "/{id}/player")
    public ResponseEntity<?> addPlayer(Authentication auth, @PathVariable(name = "id") Integer id) {
        ResponseEntity<?> response;
        User user = (User) auth.getPrincipal();
        try {
            response = this.successResponse(this.sessionService.addPlayerToSession(id, user));
        } catch (SessionNotFoundException | MaxPlayersReachedException e) {
            response = this.errorResponse(e);
        } catch (Exception e) {
            response = this.errorResponse();
        }
        return response;
    }

    @DeleteMapping(value = "/{id}/player")
    public ResponseEntity<?> removePlayer(Authentication auth, @PathVariable(name = "id") Integer id) {
        ResponseEntity<?> response;
        User user = (User) auth.getPrincipal();
        try {
            response = this.successResponse(this.sessionService.removePlayerFromSession(id, user));
        } catch (SessionNotFoundException e) {
            response = this.errorResponse(e);
        } catch (Exception e) {
            response = this.errorResponse();
        }
        return response;
    }

    @DeleteMapping(value = "/{id}/players")
    public ResponseEntity<?> removePlayers(
            Authentication auth,
            @RequestBody List<Integer> ids,
            @PathVariable(name = "id") Integer id) {

        ResponseEntity<?> response;
        User user = (User) auth.getPrincipal();
        try {
            response = this.successResponse(this.sessionService.removePlayersFromSession(id, user, ids));
        } catch (SessionNotFoundException | UnauthorizedActionException e) {
            response = this.errorResponse(e);
        } catch (Exception e) {
            response = this.errorResponse();
        }
        return response;
    }

}
