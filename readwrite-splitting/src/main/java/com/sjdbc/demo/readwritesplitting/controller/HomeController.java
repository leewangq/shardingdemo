package com.sjdbc.demo.readwritesplitting.controller;

import com.sjdbc.demo.readwritesplitting.po.Order;
import com.sjdbc.demo.readwritesplitting.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @Descirption
 * @Author lx
 * @Date 2021/11/5
 */
@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/addOrder")
    public String addOrder(){
        for (long i = 1; i <= 10; i++) {
            Order order = new Order();
            order.setId(i);
            order.setUser_id(i);
            order.setOrder_id(i+1);
            order.setCreatetime(new Date());
            order.setGoodscount(3);
            order.setGoodsname("book"+i);
            orderService.addOrder(order);
        }
        return "ok";
    }

    @GetMapping("/list")
    public String list(){
        List<Order> list= orderService.list();
        return "ok";
    }
}
