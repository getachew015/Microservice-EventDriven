package com.app.cards.config;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.github.fge.jsonpatch.JsonPatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandeler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleMessageNotReadableException(HttpMessageNotReadableException ex) {
        // Check if the cause is Jackson rejecting an unknown field
        if (ex.getCause() instanceof UnrecognizedPropertyException || ex.getCause() instanceof JsonPatchException) {
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .body("Operation not supported: One or more fields are not allowed for this resource.");
        }
        return ResponseEntity.badRequest().body("Invalid request payload.");
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<String> handleUnsupportedOperation(UnsupportedOperationException ex) {
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ex.getMessage());
    }

}
