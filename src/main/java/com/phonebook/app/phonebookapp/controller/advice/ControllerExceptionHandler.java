package com.phonebook.app.phonebookapp.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.phonebook.app.phonebookapp.dto.MessageResponse;
import com.phonebook.app.phonebookapp.exception.ResourceNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<MessageResponse> resourceNotFoundException(ResourceNotFoundException ex,
            WebRequest request) {

        MessageResponse messageResponse = new MessageResponse(ex.getMessage());

        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponse> generalExceptionHandle(Exception ex, WebRequest request) {

        MessageResponse messageResponse = new MessageResponse(ex.getMessage());

        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
