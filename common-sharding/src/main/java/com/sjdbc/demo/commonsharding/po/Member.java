package com.sjdbc.demo.commonsharding.po;

import java.util.Date;

/**
 * @Descirption
 * @Author lx
 * @Date 2021/11/12
 */
public class Member {
    private long id;
    private String name;
    private String phone;
    private Date createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
