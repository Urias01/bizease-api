package com.bizease.api.app.exceptions;

public class NotActiveCommerceException extends RuntimeException {
 
  public NotActiveCommerceException() {
    super("Comércio inativo");
  }
  
}
