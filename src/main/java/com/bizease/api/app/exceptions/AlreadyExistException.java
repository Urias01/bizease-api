package com.bizease.api.app.exceptions;

public class AlreadyExistException extends RuntimeException {

  public AlreadyExistException(String message) {
    super(message + " already exists.");
  }
}
