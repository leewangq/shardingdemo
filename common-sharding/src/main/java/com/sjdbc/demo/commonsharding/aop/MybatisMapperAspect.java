package com.sjdbc.demo.commonsharding.aop;


import org.apache.ibatis.session.SqlSessionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.DatabaseMetaData;

/**
 * @author xiang2.liu
 * @version 1.0
 * @description: mybatis mapper方法切面
 * @date 2021/9/12 21:23
 */
@Component
@Aspect
public class MybatisMapperAspect {

    @Autowired
    private SqlSessionManager sqlSessionManager;

    @Pointcut("execution(* com.sjdbc.demo.commonsharding.mapper.*.*.*(..)) ")
    public void pointcutMapper() {
    }

    @Before("pointcutMapper()")
    public void before() {
        System.out.println("before pointcutMapper");
    }

    @After("pointcutMapper()")
    public void after() {
        System.out.println("after pointcutMapper");
    }

}
