package com.sjdbc.demo.commonsharding.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiang2.liu
 * @version 1.0
 * @description: 测试ThreadLocal 不及时清理造成OOM问题
 * @date 2021/9/14 17:48
 */
public class PersonManager {
    private final ThreadLocal<Person> localPerson = new ThreadLocal<>();

    public PersonManager(){

    }

    public void startPersonManager() {
        localPerson.set(getPerson());
    }

    public boolean isPersonManagerStarted() {
        return localPerson.get() != null;
    }

    public void close(){
        //经测试两种方式都会使Person在线程结束后被gc回收
        //localPerson.set(null);
        localPerson.remove();
    }

    private Person getPerson() {
        Person person = new Person();
        List<String> nameList = new ArrayList<>();
        List<String> addressList = new ArrayList<>();
        for (int i = 0; i <= 1000000; i++) {
            nameList.add("11111111111111111111111111111111111111111111111111111111111111111111111111111111111111测试是飞洒发放大法放大发放打发大水发达是打发");
            addressList.add("22222222222222222222222222222222222222222222222222222222222222222222222222222222222222222测试是飞洒发放大法放大发放打发大水发达是打发");
        }
        person.setNameList(nameList);
        person.setAddressList(addressList);
        return person;
    }
}
