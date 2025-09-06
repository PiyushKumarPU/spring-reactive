package com.learning.reactive.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "app.token")
public class TokenProperties {

    /** Secret key for signing JWTs */
    private String secret;

    /** Access token expiry in seconds */
    private Long accessTokenExpirySeconds;

    /** Refresh token expiry in seconds */
    private Long refreshTokenExpirySeconds;
}
