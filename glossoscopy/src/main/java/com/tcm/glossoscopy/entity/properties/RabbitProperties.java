package com.tcm.glossoscopy.entity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "glossoscopy.rabbit")
@Data
public class RabbitProperties {
    private static final long serialVersionUID = 1L;
    private String host;
    private Integer port;
    private String username;
    private String password;
}
