package com.sbellali.soccerFive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadContentTypeException extends CustomHttpException {

    public BadContentTypeException(String contentType) {
        super(HttpStatus.BAD_REQUEST, "The content type " + contentType + " is't allowed for this action");
    }
}
