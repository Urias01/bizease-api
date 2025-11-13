package com.bizease.api.app.models.request;

public record AuthRequest(
    String email,
    String password,
    String slug) {

}
