package com.depromeet.watni.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "token")
public class TokenProperties {
    private int accessTokenExpired;
    private int refreshTokenExpired;
    private String androidClientId;
    private String androidClientSecret;
    private String iosClientId;
    private String iosClientSecret;
}
