package com.tcm.glossoscopy.entity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties(prefix = "glossoscopy.deepseek")
@Data
public class DeepSeekProperties implements Serializable {
    private static final long serialVersionUID = 1L;
    private String url;
    private String key;
}
