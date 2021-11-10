package com.sjdbc.demo.commonsharding.aop;


import org.apache.ibatis.session.SqlSessionManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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
//@Aspect
public class MybatisMapperAspect {

    @Autowired
    private SqlSessionManager sqlSessionManager;

    @Pointcut("execution(* com.sjdbc.demo.demo.mapper.*.*(..)) ")
    public void pointcutMapper() {
    }

    @Before("pointcutMapper()")
    public void before() {

    }

}
