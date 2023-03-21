package com.hryszkiewicz.moviebase.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
