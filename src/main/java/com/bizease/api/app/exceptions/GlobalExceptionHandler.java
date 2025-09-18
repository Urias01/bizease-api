package com.bizease.api.app.exceptions;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bizease.api.app.responses.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @Autowired
  private Environment environment;

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ApiResponse.error(ex.getMessage(), getStackTrace(ex), 400));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponse<Void>> handleGeneralException(Exception ex) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(ApiResponse.error("Unexpected error", getStackTrace(ex), 500));
  }

  @ExceptionHandler(AlreadyExistsException.class)
  public ResponseEntity<ApiResponse<Void>> handleAlreadyExists(AlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ApiResponse.error(ex.getMessage(), getStackTrace(ex), 409));
  }

  @ExceptionHandler(InvalidInputException.class)
  public ResponseEntity<ApiResponse<Void>> InvalidInput(InvalidInputException ex) {
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
        .body(ApiResponse.error(ex.getMessage(), getStackTrace(ex), 422));
  }

  @ExceptionHandler(NotActiveCommerceException.class)
  public ResponseEntity<ApiResponse<Void>> NotActiveCommerce(NotActiveCommerceException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ApiResponse.error(ex.getMessage(), getStackTrace(ex), 409));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ApiResponse<Void>> NotFound(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ApiResponse.error(ex.getMessage(), getStackTrace(ex), 404));
  }

  @ExceptionHandler(UnauthorizedAccessException.class)
  public ResponseEntity<ApiResponse<Void>> UnauthorizedAccess(UnauthorizedAccessException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT)
        .body(ApiResponse.error(ex.getMessage(), getStackTrace(ex), 403));
  }

  private String getStackTrace(Exception ex) {
    if (isDev()) {
      return Arrays.stream(ex.getStackTrace())
          .map(StackTraceElement::toString)
          .collect(Collectors.joining("\n"));
    }
    return null;
  }

  private boolean isDev() {
    return Arrays.asList(environment.getActiveProfiles()).contains("dev");
  }
}
