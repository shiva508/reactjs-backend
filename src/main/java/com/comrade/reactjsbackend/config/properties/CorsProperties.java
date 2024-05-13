package com.comrade.reactjsbackend.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "reactjs.web")
@Data
public class CorsProperties {
    private String baseUrl;
    private String path;
}
