package com.bizease.api.app.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

  private boolean success;
  private int total;
  private T data;
  private int code;
  private String message;
  private String stackTrace;

  public static <T> ApiResponse<T> success(T data, int code) {
    ApiResponse<T> response = new ApiResponse<T>();
    response.success = true;
    response.data = data;
    response.code = code;
    return response;
  }

  public static <T> ApiResponse<T> error(String message, String stackTrace, int code) {
    ApiResponse<T> response = new ApiResponse<T>();
    response.success = false;
    response.code = code;
    response.message = message;
    response.stackTrace = stackTrace;
    return response;
  }

}
