package com.tcm.glossoscopy.config;

import com.tcm.glossoscopy.entity.properties.AliOssProperties;
import com.tcm.glossoscopy.entity.properties.AliSmsProperties;
import com.tcm.glossoscopy.utils.AliOssUtil;
import com.tcm.glossoscopy.utils.AliSmsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class AliyunConfig {
    /**
     * 创建阿里云短信服务工具对象
     *
     * @param aliSmsProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public AliSmsUtil aliSmsUtil(AliSmsProperties aliSmsProperties) {
        log.info("开始创建阿里云短信服务工具对象");
        return new AliSmsUtil(aliSmsProperties.getRegionId(), aliSmsProperties.getAccessKeyId(),
                aliSmsProperties.getAccessKeySecret(), aliSmsProperties.getTemplateCode(),
                aliSmsProperties.getSignName());
    }


    /**
     * 创建阿里云对象存储工具对象
     *
     * @param aliOssProperties
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("开始创建阿里云文件上传工具对象");
        return new AliOssUtil(aliOssProperties.getEndpoint(), aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(), aliOssProperties.getBucketName());
    }
}
