package com.sjdbc.demo.commonsharding.thread;

import java.util.List;

/**
 * @author xiang2.liu
 * @version 1.0
 * @description: TODO
 * @date 2021/9/14 17:47
 */
public class Person {
    public List<String> getNameList() {
        return nameList;
    }

    public void setNameList(List<String> nameList) {
        this.nameList = nameList;
    }

    public List<String> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addressList) {
        this.addressList = addressList;
    }

    private List<String> nameList;

    private List<String> addressList;
}
