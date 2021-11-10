package com.sjdbc.demo.commonsharding.proxy;

/**
 * @author xiang2.liu
 * @version 1.0
 * @description: TODO
 * @date 2021/9/10 10:14
 */
public class Customer implements IBuyCar {

    private int cash;

    @Override
    public void buyCar() {
        System.out.println(String.format("买车共花费:%s元", cash));
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int cash) {
        this.cash = cash;
    }
}
