package com.bizease.api.app.exceptions;

public class AlreadyExistsException extends RuntimeException {
 
  public AlreadyExistsException(String message) {
    super(message + " já foi cadastrado");
  }
}
