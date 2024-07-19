package com.example.match.exceptions;

import com.example.match.model.StandardResponse;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Level;

@Log
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardResponse> handleException(Exception exception) {
        log.log(Level.SEVERE, "handleException " + exception.getMessage());
        if (exception instanceof UnAuthorizedAccess || exception instanceof ExpiredJwtException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StandardResponse(exception.getMessage()));
        } else if (exception instanceof RateLimitExceededException) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(new StandardResponse(exception.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new StandardResponse(exception.getMessage()));
    }
}
