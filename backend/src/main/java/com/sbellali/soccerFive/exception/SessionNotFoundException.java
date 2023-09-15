package com.sbellali.soccerFive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SessionNotFoundException extends CustomHttpException {

    public SessionNotFoundException() {
        super(HttpStatus.NOT_FOUND, "session not found.");
    }

    public SessionNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

}
