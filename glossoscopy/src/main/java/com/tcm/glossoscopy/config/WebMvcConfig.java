package com.tcm.glossoscopy.config;

import com.tcm.glossoscopy.entity.properties.DeepSeekProperties;
import com.tcm.glossoscopy.interceptor.GlobalInterceptor;
import com.tcm.glossoscopy.json.JacksonObjectMapper;
import com.tcm.glossoscopy.utils.DeepSeekUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import javax.annotation.Resource;
import java.util.List;


/**
 * 配置类，注册web层相关组件
 */

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Resource
    private GlobalInterceptor globalInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器");
        registry.addInterceptor(globalInterceptor)
                .addPathPatterns("/**");
    }

    /**
     * 通过knife4j生成接口文档
     *
     * @return
     */
    @Bean
    public Docket docket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("舌诊项目接口文档")
                .version("1.0")
                .description("舌诊项目接口文档")
                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .groupName("管理端接口")
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tcm.glossoscopy.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }

    /**
     * 设置静态资源映射
     *
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 将对象转换器添加至消息转换器并且放置于容器的优先位置
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        //需要为消息转换器设置一个对象转换器，对象转换器可以将java对象序列化为json对象
        converter.setObjectMapper(new JacksonObjectMapper());
        //将自己的消息转换器加入容器中
        converters.add(0, converter);
    }

    /**
     * 创建deepseek服务工具对象
     *
     * @param deepSeekProperties
     * @return
     */
    @Bean
    public DeepSeekUtil deepSeekUtil(DeepSeekProperties deepSeekProperties) {
        log.info("开始创建deepseek服务工具对象");
        return new DeepSeekUtil(deepSeekProperties.getUrl(), deepSeekProperties.getKey());
    }


}
