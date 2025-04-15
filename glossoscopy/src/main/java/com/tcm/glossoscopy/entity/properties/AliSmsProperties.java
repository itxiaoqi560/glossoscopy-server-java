package com.tcm.glossoscopy.entity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties(prefix = "glossoscopy.alisms")
@Data
public class AliSmsProperties implements Serializable {
    private static final long serialVersionUID = 1L;
    private String regionId;
    private String accessKeyId;
    private String accessKeySecret;
    private String templateCode;
    private String signName;
}
