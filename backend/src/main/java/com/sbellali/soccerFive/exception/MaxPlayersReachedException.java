package com.sbellali.soccerFive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class MaxPlayersReachedException extends CustomHttpException {

    public MaxPlayersReachedException() {
        super(HttpStatus.UNAUTHORIZED, "The maximum number of players has been reached.");
    }

    public MaxPlayersReachedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

}
