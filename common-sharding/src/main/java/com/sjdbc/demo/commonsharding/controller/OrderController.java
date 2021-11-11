package com.sjdbc.demo.commonsharding.controller;

import com.sjdbc.demo.commonsharding.po.Order;
import com.sjdbc.demo.commonsharding.serivce.OrderService;
import org.apache.shardingsphere.transaction.annotation.ShardingTransactionType;
import org.apache.shardingsphere.transaction.core.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xiang2.liu
 * @version 1.0
 * @description: 订单实体控制器
 * @date 2021/9/9 11:35
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/addOrder")
    @Transactional(rollbackFor = Exception.class)
    @ShardingTransactionType(TransactionType.XA)
    public String addOrder(){
       /* try(HintManager hintManager=HintManager.getInstance()){
            hintManager.addDatabaseShardingValue("t_order",1L);
            hintManager.addTableShardingValue("t_order",1L);*/
            for (long i = 1; i <= 80; i++) {
                Order order = new Order();
                order.setUser_id(i);
                order.setOrder_id(i+1);
                order.setCreatetime(new Date());
                order.setGoodscount(3);
                order.setGoodsname("book"+i);
                orderService.addOrder(order);
            }
        //}
        return "ok";
    }

    @GetMapping("/list")
    public String list() {
       /* System.out.println(String.format("线程id:%s", Thread.currentThread().getId()));
        for (int i = 0; i < 5; i++) {
            orderService.list();
        }*/
        orderService.list();
        return "ok";
    }

    @GetMapping("/orderList")
    public String orderList(long userId) {
        List<Order> list = orderService.getOrderByUserId(userId);
       /* List<Long> orderIdList=new LinkedList<>();
        orderIdList.add(1L);
        orderIdList.add(3L);
        List<Order> orderList=orderService.getOrderByUserIdRange(1L,3L);*/
        return "ok";
    }
}
