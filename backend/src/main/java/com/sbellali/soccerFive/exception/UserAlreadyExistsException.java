package com.sbellali.soccerFive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException() {
        super("username or/and email already exit");
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
