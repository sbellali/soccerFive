package com.sbellali.soccerFive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class SessionNotFoundException extends RuntimeException {

    public SessionNotFoundException() {
        super("session not found.");
    }

    public SessionNotFoundException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }

}
