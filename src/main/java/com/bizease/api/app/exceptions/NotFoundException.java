package com.bizease.api.app.exceptions;

public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {
    super(message + " não encontrado(a)");
  }
  
}
