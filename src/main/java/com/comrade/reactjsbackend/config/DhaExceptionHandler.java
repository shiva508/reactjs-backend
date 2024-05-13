package com.comrade.reactjsbackend.config;

import com.comrade.reactjsbackend.model.exception.ApiErrorResponse;
import com.comrade.reactjsbackend.model.exception.RecordNotFoundException;
import com.comrade.reactjsbackend.util.DhaConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class DhaExceptionHandler {

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ApiErrorResponse>  recordNotFound(RecordNotFoundException exception){
        return new ResponseEntity<>(ApiErrorResponse.builder()
                .message(DhaConstants.COMMON_MSG)
                .timestamp(new Date())
                .error(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build(),HttpStatus.NOT_FOUND) ;
    }

}
