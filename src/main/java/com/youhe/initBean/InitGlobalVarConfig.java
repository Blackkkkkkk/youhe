package com.youhe.initBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 作用：全局静态变量<br>
 * 说明：(无)
 *
 * @author Kalvin
 * @Date 2019年04月17日 15:32
 */
@Order(2)
@Component
public class InitGlobalVarConfig implements ApplicationRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(InitGlobalVarConfig.class);

    @Resource
    private ThymeleafViewResolver thymeleafViewResolver;

    @Override
    public void run(ApplicationArguments args) {
        LOGGER.info("youhe 项目启动成功！");
        Map<String, Object> vars = new HashMap<>();
        vars.put("allMenu","");
        thymeleafViewResolver.setStaticVariables(vars);
    }
}
