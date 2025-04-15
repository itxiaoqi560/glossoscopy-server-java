package com.tcm.glossoscopy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
//@EnableCaching
@EnableScheduling
@EnableAspectJAutoProxy
@ServletComponentScan
@EnableTransactionManagement
public class GlossoscopyApplication {
    public static void main(String[] args) {
        SpringApplication.run(GlossoscopyApplication.class, args);
    }

}
