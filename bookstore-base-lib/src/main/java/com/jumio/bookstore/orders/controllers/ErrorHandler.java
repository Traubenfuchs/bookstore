package com.jumio.bookstore.orders.controllers;

import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

import javax.persistence.*;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
  @ExceptionHandler(ResponseStatusException.class)
  protected ResponseEntity<ExceptionResponse> handle(ResponseStatusException ex) {
    log.error("ErrorHandler caught a ResponseStatusException", ex);
    return ResponseEntity
      .status(ex.getStatus())
      .body(new ExceptionResponse(ex.getStatus(), ex.getReason()));
  }

  @ExceptionHandler(EntityNotFoundException.class)
  protected ResponseEntity<ExceptionResponse> handle(EntityNotFoundException ex) {
    log.error("ErrorHandler caught an EntityNotFoundException", ex);
    return ResponseEntity
      .status(NOT_FOUND)
      .body(new ExceptionResponse(NOT_FOUND, "The requested resource does not exist or you have no access to it."));
  }

  @ExceptionHandler(Exception.class)
  protected ResponseEntity<ExceptionResponse> handle(Exception ex) {
    log.error("ErrorHandler caught an Exception. Custom handling for this exception should be implemented!", ex);
    return ResponseEntity
      .status(INTERNAL_SERVER_ERROR)
      .body(new ExceptionResponse(INTERNAL_SERVER_ERROR, ex.getMessage()));
  }

  @Data
  private static class ExceptionResponse {
    public ExceptionResponse(HttpStatus httpStatus, String message) {
      this.error = httpStatus.name();
      this.code = httpStatus.value();
      this.message = message;
    }

    private final int code;
    private final String error;
    private final String message;
  }
}
