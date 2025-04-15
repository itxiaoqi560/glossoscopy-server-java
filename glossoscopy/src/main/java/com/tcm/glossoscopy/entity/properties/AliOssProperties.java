package com.tcm.glossoscopy.entity.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@ConfigurationProperties(prefix = "glossoscopy.alioss")
@Data
public class AliOssProperties implements Serializable {
    private static final long serialVersionUID = 1L;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
