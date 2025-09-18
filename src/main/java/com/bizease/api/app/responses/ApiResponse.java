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
  private Integer total;
  private T data;
  private int code;
  private String message;
  private String stackTrace;

  public static <T> ApiResponse<T> success(T data, int code) {
    return new ApiResponse<>(true, null, data, code, null, null);
  }

  public static <T> ApiResponse<T> success(T data, int code, int total) {
    return new ApiResponse<>(true, total, data, code, null, null);
  }

  public static <T> ApiResponse<T> error(String message, String stackTrace, int code) {
    return new ApiResponse<>(false, null, null, code, message, stackTrace);
  }

}
