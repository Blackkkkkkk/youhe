package com.youhe.initBean.webMvcConfig;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.LocaleResolver;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //将对应的静态文件访问路径映射到文件对应位置，需在shiro配置匿名访问，否则会对路径进行拦截
        /*
        registry.addResourceHandler("/bootstrap/**").addResourceLocations("classpath:/static/bootstrap/");
        registry.addResourceHandler("/layui/**").addResourceLocations("classpath:/static/layui/");
        registry.addResourceHandler("/diagram-viewer/**").addResourceLocations("classpath:/static/diagram-viewer/");
        registry.addResourceHandler("/editor-app/**").addResourceLocations("classpath:/static/editor-app/");
        registry.addResourceHandler("/ztree/**").addResourceLocations("classpath:/static/ztree/");
        registry.addResourceHandler("/layerslider/skins/fullwidth/**").addResourceLocations("classpath:/static/bootstrap/" +
                "user/plugins/layerslider/skins/fullwidth/");
*/

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("/layerslider/skins/fullwidth/**").addResourceLocations("classpath:/static/bootstrap/" +
                "user/plugins/layerslider/skins/fullwidth/");

        // 获取项目编译后的路径
        String realPath = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/templates/upload/";
        registry.addResourceHandler("/templates/upload/**").addResourceLocations("file:" + realPath);



        // 获取项目编译后的路径
        String realPath1 = ClassUtils.getDefaultClassLoader().getResource("").getPath() + "/static/"
                +"bootstrap/js/project/user/shop/shoppingCart/picture/";
        registry.addResourceHandler("/resources/static/**").addResourceLocations("file:" + realPath1);

        super.addResourceHandlers(registry);
    }


}