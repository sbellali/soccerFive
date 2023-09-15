package com.sbellali.soccerFive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedActionException extends CustomHttpException {

    public UnauthorizedActionException() {
        super(HttpStatus.UNAUTHORIZED, "unauthorized action.");
    }

    public UnauthorizedActionException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
