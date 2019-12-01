package com.jumio.bookstore.controllers;

import com.netflix.hystrix.exception.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.*;
import org.springframework.web.server.*;

import javax.persistence.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
  @ExceptionHandler(HttpClientErrorException.class)
  ResponseEntity<ExceptionResponse> handle(HttpClientErrorException ex) {
    log.error("ErrorHandler caught a HttpClientErrorException", ex);
    return ResponseEntity
      .status(ex.getStatusCode())
      .body(new ExceptionResponse(ex.getStatusCode(), "Partner system returned 4xx."));
  }

  @ExceptionHandler(HttpServerErrorException.class)
  ResponseEntity<ExceptionResponse> handle(HttpServerErrorException ex) {
    log.error("ErrorHandler caught a HttpServerErrorException", ex);
    return ResponseEntity
      .status(BAD_GATEWAY)
      .body(new ExceptionResponse(BAD_GATEWAY, "Partner system returned 5xx."));
  }

  @ExceptionHandler(UnknownHttpStatusCodeException.class)
  ResponseEntity<ExceptionResponse> handle(UnknownHttpStatusCodeException ex) {
    log.error("ErrorHandler caught a UnknownHttpStatusCodeException", ex);
    return ResponseEntity
      .status(BAD_GATEWAY)
      .body(new ExceptionResponse(BAD_GATEWAY, "Partner system returned unknown error."));
  }

  @ExceptionHandler(ResponseStatusException.class)
  ResponseEntity<ExceptionResponse> handle(ResponseStatusException ex) {
    log.error("ErrorHandler caught a ResponseStatusException", ex);
    return ResponseEntity
      .status(ex.getStatus())
      .body(new ExceptionResponse(ex.getStatus(), ex.getReason()));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  ResponseEntity<ExceptionResponse> handle(EntityNotFoundException ex) {
    log.error("ErrorHandler caught an EntityNotFoundException", ex);
    return ResponseEntity
      .status(NOT_FOUND)
      .body(new ExceptionResponse(NOT_FOUND, "The requested resource does not exist or you have no access to it."));
  }

  @ExceptionHandler(HystrixRuntimeException.class)
  ResponseEntity<ExceptionResponse> handle(HystrixRuntimeException ex) {
    log.error("ErrorHandler caught an HystrixRuntimeException!", ex);
    return ResponseEntity
      .status(BAD_GATEWAY)
      .body(new ExceptionResponse(BAD_GATEWAY, "A partner system is unavailable. Please try again later."));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  ResponseEntity<ExceptionResponse> handle(MethodArgumentNotValidException ex) {
    log.error("ErrorHandler caught an MethodArgumentNotValidException!", ex);
    return ResponseEntity
      .status(BAD_REQUEST)
      .body(new ExceptionResponse(BAD_REQUEST, ex.getMessage()));
  }

  @ExceptionHandler(IllegalStateException.class)
  ResponseEntity<ExceptionResponse> handle(IllegalStateException ex) {
    log.error("ErrorHandler caught an IllegalStateException.", ex);

    if (ex.getMessage().startsWith("No instances available for ")) {
      return ResponseEntity
        .status(BAD_GATEWAY)
        .body(new ExceptionResponse(BAD_GATEWAY, "A partner system is unavailable. Please try again later."));
    }

    return handleDefault(ex);
  }

  @ExceptionHandler(Exception.class)
  private ResponseEntity<ExceptionResponse> handleDefault(Exception ex) {
    log.error("ErrorHandler caught an Exception. Custom handling for this exception should be implemented!", ex);
    return ResponseEntity
      .status(INTERNAL_SERVER_ERROR)
      .body(new ExceptionResponse(INTERNAL_SERVER_ERROR, ex.getMessage()));
  }

  @Getter
  private static class ExceptionResponse {
    ExceptionResponse(HttpStatus httpStatus, String message) {
      this.error = httpStatus.name();
      this.code = httpStatus.value();
      this.message = message;
    }

    private final int code;
    private final String error;
    private final String message;
  }
}