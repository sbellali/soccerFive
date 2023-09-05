package com.sbellali.soccerFive.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sbellali.soccerFive.dto.ErrorMessage;

public class AbsractController {

    protected ResponseEntity<?> successResponse(Object response) {
        return ResponseEntity.ok().body(response);
    }

    protected ResponseEntity<?> successResponse(HttpStatus httpStatus, Object response) {
        return response(httpStatus, response);
    }

    protected ResponseEntity<?> errorResponse() {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    protected ResponseEntity<?> errorResponse(HttpStatus httpStatus, Exception e) {
        return errorResponse(httpStatus, e.getMessage());
    }

    protected ResponseEntity<?> errorResponse(HttpStatus httpStatus, String message) {
        return response(httpStatus, new ErrorMessage(httpStatus.value(), message));
    }

    protected ResponseEntity<?> response(HttpStatus httpStatus, Object response) {
        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }
}
