package com.sjdbc.demo.commonsharding.aop;

import com.sjdbc.demo.commonsharding.thread.PersonManager;
import org.apache.ibatis.session.SqlSessionManager;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author xiang2.liu
 * @version 1.0
 * @description: TODO
 * @date 2021/9/14 9:32
 */
@Component
@Aspect
public class ControllerAspect {
    @Autowired
    private SqlSessionManager sqlSessionManager;


    @Pointcut("execution(* com.sjdbc.demo.demo.controller.*.*(..)) ")
    public void pointcutController() {

    }

    @Before("pointcutController()")
    public void before() {
        if (sqlSessionManager != null && !sqlSessionManager.isManagedSessionStarted()) {
            sqlSessionManager.startManagedSession();
        }
    }


    @After("pointcutController()")
    public void after() {
        if (sqlSessionManager != null && sqlSessionManager.isManagedSessionStarted()) {
            sqlSessionManager.close();
        }
       /* if(personManager!=null&&personManager.isPersonManagerStarted()){
            personManager.close();
            System.out.println("close");
        }*/
    }
}
