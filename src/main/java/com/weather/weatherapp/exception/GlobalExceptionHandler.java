package com.weather.weatherapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(HttpClientErrorException.Unauthorized e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                ErrorResponse.builder()
                        .errorCode(401)
                        .details("Unauthorized — API key is invalid or inactive")
                        .message(e.getResponseBodyAsString())
                        .localTime(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<ErrorResponse> handleNotFound(HttpClientErrorException.NotFound e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder()
                        .errorCode(404)
                        .details("City not found — please check the city name")
                        .message(e.getResponseBodyAsString())
                        .localTime(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(HttpClientErrorException.BadRequest e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ErrorResponse.builder()
                        .errorCode(400)
                        .details("Bad request — city name may be empty or malformed")
                        .message(e.getResponseBodyAsString())
                        .localTime(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpClientError(HttpClientErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body(
                ErrorResponse.builder()
                        .errorCode(e.getStatusCode().value())
                        .details("HTTP error from weather API")
                        .message(e.getResponseBodyAsString())
                        .localTime(LocalDateTime.now())
                        .build()
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorResponse.builder()
                        .errorCode(500)
                        .details("Internal server error")
                        .message(e.getMessage())
                        .localTime(LocalDateTime.now())
                        .build()
        );
    }
}