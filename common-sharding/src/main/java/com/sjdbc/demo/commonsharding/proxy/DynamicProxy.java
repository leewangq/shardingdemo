package com.sjdbc.demo.commonsharding.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author xiang2.liu
 * @version 1.0
 * @description: TODO
 * @date 2021/9/10 10:17
 */
public class DynamicProxy {

    private Object obj;

    public DynamicProxyImpl getDynamicProxy() {
        return dynamicProxy;
    }

    private DynamicProxyImpl dynamicProxy;


    public DynamicProxy(Object obj) {
        this.obj = obj;
        this.dynamicProxy = new DynamicProxy.DynamicProxyImpl();
    }

    public class DynamicProxyImpl implements InvocationHandler {
        public DynamicProxyImpl() {

        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = method.invoke(DynamicProxy.this.obj, args);
            return result;
        }
    }

}
