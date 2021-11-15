package com.sjdbc.demo.commonsharding.mapper.member;

import com.sjdbc.demo.commonsharding.po.Member;

import java.util.List;

/**
 * @Descirption
 * @Author lx
 * @Date 2021/11/12
 */
public interface MemberMapper {
    void add(Member member);
    List<Member> list();
}
