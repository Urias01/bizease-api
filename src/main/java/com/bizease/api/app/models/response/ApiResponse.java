package com.bizease.api.app.models.response;

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

  private Boolean success;
  private String message;
  private Integer total;
  private T data;

  public static <T> ApiResponse<T> success(T data) {
    ApiResponse<T> response = new ApiResponse<T>();
    response.setSuccess(true);
    response.setData(data);
    return response;
  };

  public static <T> ApiResponse<T> success(T data, int total) {
    ApiResponse<T> response = new ApiResponse<T>();
    response.setSuccess(true);
    response.setData(data);
    response.setTotal(total);
    return response;
  };

  public static <T> ApiResponse<T> failure(String message) {
    ApiResponse<T> response = new ApiResponse<T>();
    response.setSuccess(false);
    response.setMessage(message);
    return response;
  };
}
