package com.sjdbc.demo.readwritesplitting.services;

import com.sjdbc.demo.readwritesplitting.po.Order;

import java.util.List;

/**
 * @Descirption
 * @Author lx
 * @Date 2021/11/5
 */
public interface OrderService {
    void addOrder(Order order);
    List<Order> list();
}
