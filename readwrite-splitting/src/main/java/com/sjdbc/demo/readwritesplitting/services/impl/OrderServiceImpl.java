package com.sjdbc.demo.readwritesplitting.services.impl;

import com.sjdbc.demo.readwritesplitting.mapper.OrderMapper;
import com.sjdbc.demo.readwritesplitting.po.Order;
import com.sjdbc.demo.readwritesplitting.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Descirption
 * @Author lx
 * @Date 2021/11/5
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public void addOrder(Order order) {
        orderMapper.addOrder(order);
    }

    @Override
    public List<Order> list() {
        return orderMapper.list();
    }
}
