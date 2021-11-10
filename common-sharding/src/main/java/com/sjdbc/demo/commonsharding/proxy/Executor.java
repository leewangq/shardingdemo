package com.sjdbc.demo.commonsharding.proxy;

import java.lang.reflect.Proxy;

/**
 * @author xiang2.liu
 * @version 1.0
 * @description: TODO
 * @date 2021/9/10 11:47
 */
public class Executor {
    public Executor() {
        Customer customer1 = new Customer();
        customer1.setCash(100);
        DynamicProxy dynamicProxy = new DynamicProxy(customer1);
        //https://blog.csdn.net/u012326462/article/details/81293186
        //loader: 用哪个类加载器去加载代理对象
        IBuyCar iBuyCar = (IBuyCar) Proxy.newProxyInstance(dynamicProxy.getClass().getClassLoader(), new Class[]{IBuyCar.class}, dynamicProxy.getDynamicProxy());
        iBuyCar.buyCar();
    }

    public static void main(String[] args) {
        new Executor();
    }
}
