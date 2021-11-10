package com.sjdbc.demo.commonsharding.serivce;

import com.sjdbc.demo.commonsharding.po.Order;

import java.util.List;

/**
 * @author xiang2.liu
 * @version 1.0
 * @description: TODO
 * @date 2021/9/9 11:32
 */
public interface OrderService {
    Long addOrder(Order order);

    List<Order> list();

    Order findById(Long id);

    List<Order> getOrderByUserIdAndOrderId(long userId,long orderId);

    List<Order> getOrderByUserId(long userId);

    List<Order> getOrderByUserIdList(List<Long> userIdList);

    List<Order> getOrderByUserIdRange(long minUserId,long maxUserId);
}
