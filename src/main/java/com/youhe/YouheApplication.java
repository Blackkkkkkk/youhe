package com.youhe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.activiti.spring.boot.SecurityAutoConfiguration.class,
        DataSourceAutoConfiguration.class
})
@EnableAspectJAutoProxy(exposeProxy = true)
public class YouheApplication {

    public static void main(String[] args) {
        SpringApplication.run(YouheApplication.class, args);
    }

}
