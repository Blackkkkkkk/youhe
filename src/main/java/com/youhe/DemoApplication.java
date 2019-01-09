package com.youhe;

import org.apache.commons.io.FileUtils;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.activiti.engine.ProcessEngine;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.File;
import java.io.InputStream;
import java.util.List;

@SpringBootApplication
@ComponentScan({"com.youhe", "org.activiti.rest.diagram"})
@EnableTransactionManagement
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableAutoConfiguration(exclude = {org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class, org.activiti.spring.boot.SecurityAutoConfiguration.class})
@MapperScan("com.youhe.mapper")
public class DemoApplication {

    @Autowired
    ProcessEngine processEngine;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
