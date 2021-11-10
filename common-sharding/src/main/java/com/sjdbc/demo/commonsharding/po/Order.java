package com.sjdbc.demo.commonsharding.po;


import java.util.Date;

/**
 * @author xiang2.liu
 * @version 1.0
 * @description: 订单表
 * @date 2021/9/9 9:43
 */
public class Order {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public int getGoodscount() {
        return goodscount;
    }

    public void setGoodscount(int goodscount) {
        this.goodscount = goodscount;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    private long id;

    private long user_id;

    private long order_id;

    private Date createtime;

    private int goodscount;

    private String goodsname;
}
