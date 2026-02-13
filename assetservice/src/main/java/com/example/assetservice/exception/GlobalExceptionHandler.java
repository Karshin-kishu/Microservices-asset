package com.example.assetservice.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalExceptionHandler {
	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<?> notFound(ResourceNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                .body(Map.of("error", "NOT_FOUND", "message", ex.getMessage()));
	    }

	    @ExceptionHandler(BadRequestException.class)
	    public ResponseEntity<?> badRequest(BadRequestException ex) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(Map.of("error", "BAD_REQUEST", "message", ex.getMessage()));
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<?> general(Exception ex) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body(Map.of("error", "SERVER_ERROR", "message", ex.getMessage()));
	    }
}
