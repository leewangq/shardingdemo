package com.sjdbc.demo.commonsharding.serivce.impl;

import com.sjdbc.demo.commonsharding.mapper.order.OrderMapper;
import com.sjdbc.demo.commonsharding.po.Order;
import com.sjdbc.demo.commonsharding.serivce.OrderService;
import org.apache.ibatis.session.SqlSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiang2.liu
 * @version 1.0
 * @description: 订单服务
 * @date 2021/9/9 11:32
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Long addOrder(Order order) {
        return orderMapper.addOrder(order);
    }

    @Override
    public List<Order> list() {
        for (int i = 0; i < 2; i++) {
            orderMapper.list();
        }
        orderMapper.list();
        return null;
    }

    @Override
    public Order findById(Long id) {
        return orderMapper.findById(id);
    }

    @Override
    public List<Order> getOrderByUserIdAndOrderId(long userId, long orderId) {
        return orderMapper.getOrderByUserIdAndOrderId(userId,orderId);
    }

    @Override
    public List<Order> getOrderByUserId(long userId) {
        return orderMapper.getOrderByUserId(userId);
    }

    @Override
    public List<Order> getOrderByUserIdList(List<Long> userIdList) {
        return orderMapper.getOrderByUserIdList(userIdList);
    }

    @Override
    public List<Order> getOrderByUserIdRange(long minUserId, long maxUserId) {
        return orderMapper.getOrderByUserIdRange(minUserId,maxUserId);
    }
}
