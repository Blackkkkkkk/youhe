package com.youhe.initBean.webMvcConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将对应的静态文件访问路径映射到文件对应位置，需在shiro配置匿名访问，否则会对路径进行拦截
        registry.addResourceHandler("/bootstrap/**").addResourceLocations("classpath:/static/bootstrap/");
        registry.addResourceHandler("/layui/**").addResourceLocations("classpath:/static/layui/");
        registry.addResourceHandler("/diagram-viewer/**").addResourceLocations("classpath:/static/diagram-viewer/");
        registry.addResourceHandler("/editor-app/**").addResourceLocations("classpath:/static/editor-app/");
        registry.addResourceHandler("/ztree/**").addResourceLocations("classpath:/static/ztree/");


        // 获取项目编译后的路径
        String realPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/templates/upload/";
        registry.addResourceHandler("/webapp/upload/**").addResourceLocations("file:" + realPath);

        super.addResourceHandlers(registry);
    }


}