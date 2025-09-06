package com.learning.reactive.data.dto;

public record AuthenticationResponse(String userId, String token, String refreshToken) {
}
