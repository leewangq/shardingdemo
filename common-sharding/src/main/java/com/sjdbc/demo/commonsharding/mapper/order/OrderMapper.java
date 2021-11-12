package com.sjdbc.demo.commonsharding.mapper.order;

import com.sjdbc.demo.commonsharding.po.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiang2.liu
 * @version 1.0
 * @description: TODO
 * @date 2021/9/9 9:48
 */
public interface OrderMapper {
    Long addOrder(Order order);

    List<Order> list();

    Order findById(Long id);

    List<Order> getOrderByUserIdAndOrderId(@Param("userId") long userId,@Param("orderId")long orderId);

    List<Order> getOrderByUserId(@Param("userId") long userId);

    List<Order> getOrderByUserIdList(@Param("userIdList") List<Long> userIdList);

    List<Order> getOrderByUserIdRange(@Param("minUserId")long minUserId,@Param("maxUserId")long maxUserId);
}
