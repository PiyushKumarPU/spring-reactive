package com.learning.reactive.data.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;

    private String password;
}
