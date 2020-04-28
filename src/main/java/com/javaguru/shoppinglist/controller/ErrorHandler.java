package com.javaguru.shoppinglist.controller;

import com.javaguru.shoppinglist.service.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

public interface ErrorHandler {
    @ExceptionHandler
    default ResponseEntity<Response> handleException(RuntimeException e) {
        e.printStackTrace();

        Response response = new Response();
        response.message = e.getMessage();

        if (e instanceof NoSuchElementException) {
            response.error = "Resource not found";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } else if (e instanceof HttpMessageNotReadableException) {
            response.error = "Bad request format";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else if (e instanceof ValidationException) {
            response.error = "Validation exception";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } else {
            response.error = "Internal error";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    class Response {
        private String error;
        private String message;

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
