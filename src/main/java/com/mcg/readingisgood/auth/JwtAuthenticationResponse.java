package com.mcg.readingisgood.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponse {

    private String accessToken;
    private String tokenType = "Bearer";

    JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
