package com.sbellali.soccerFive.exception;

import org.springframework.http.HttpStatus;

public class CustomHttpException extends RuntimeException {
    private HttpStatus httpStatus;

    public CustomHttpException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
