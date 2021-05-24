package com.walmart.drivers.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionHandlers {
    
    @ExceptionHandler(InputParamException.class)
	public ResponseEntity<ErrResponse> exceptionScoreCardHandler(Exception ex) {
		ErrResponse error = new ErrResponse();
		error.setErrorCode(HttpStatus.BAD_REQUEST.value());
		error.setMessage(ex.getMessage());
		log.error(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
}
