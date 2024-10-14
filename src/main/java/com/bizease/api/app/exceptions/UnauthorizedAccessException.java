package com.bizease.api.app.exceptions;

public class UnauthorizedAccessException extends RuntimeException {
 
  public UnauthorizedAccessException() {
    super("Você não tem permissão para realizar essa atividade!");
  }
}
