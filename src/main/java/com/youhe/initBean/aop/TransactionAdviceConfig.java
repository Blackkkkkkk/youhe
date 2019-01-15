package com.youhe.initBean.aop;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.youhe.controller.loginController.LoginController;
import com.youhe.utils.shiro.ShiroUser;
import com.youhe.utils.shiro.ShiroUserUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.NameMatchTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;


/**
 * @author xieweiqiang
 * @version V1.0
 * @Title: TxAdviceInterceptor.java
 * @Package com.cloud.aop
 * @Description:
 * @date 2018年12月8日 上午10:37:31
 * aop 处理事务
 */
@Aspect
@Configuration
public class TransactionAdviceConfig {
    // private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.youhe.serviceImpl.*.*(..))";

    private static final Logger log = LoggerFactory.getLogger(TransactionAdviceConfig.class);

    private static final int TX_METHOD_TIMEOUT = 5;
    private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.youhe.biz.*.*(..))";

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
         /*只读事务，不做更新操作*/
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        /*当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务*/
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(
                Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        requiredTx.setTimeout(TX_METHOD_TIMEOUT);
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("controller*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        source.setNameMap(txMap);
        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, source);
        return txAdvice;
    }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
        //return new DefaultPointcutAdvisor(pointcut, txAdvice);
    }

/*
    //该方法是一个前置通知：在目标方法执行之前执行
    @Before(value = "execution(* com.youhe..*())")
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("the method " + methodName + " begin with " + Arrays.asList(joinPoint.getArgs()));
    }*/

/*
    //该方法是一个后置通知：在目标方法执行之后执行（无论是否发生异常）
    @After(value = "execution(* com.youhe..*())")
    public void afterMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("the method " + methodName + " end");
    }*/

    // 1、前置通知： 在目标方法开始之前执行（就是要告诉该方法要在哪个类哪个方法前执行）

    @Before(value = "execution(* com.youhe..*())  && !execution(* com.youhe.initBean..*())")
    public void beforeMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();

        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String name = "";

        if (shiroUser.getUserName() != null && shiroUser.getUserName() != "") {
            name = shiroUser.getUserName();
        } else {
            name = "游客";
        }
        log.info("【" + name + "】调用了:" + className + "类的" + methodName + "方法开始了");
    }


    @After(value = "execution(* com.youhe..*()) && !execution(* com.youhe.initBean..*())")
    public void afterMethod(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();

        ShiroUser shiroUser = ShiroUserUtils.getShiroUser();
        String name = "";

        if (shiroUser.getUserName() != null && shiroUser.getUserName() != "") {
            name = shiroUser.getUserName();
        } else {
            name = "游客";
        }
        log.info("【" + name + "】调用完了:" + className + "类的" + methodName + "方法");

    }
}