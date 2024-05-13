package com.comrade.reactjsbackend.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "reactjs.security")
@Data
public class SecurityProperties {
    private String secretKey;
    private Long expirationDuration;
    private String tokenHeader;
    private String tokenPrefix;
}
