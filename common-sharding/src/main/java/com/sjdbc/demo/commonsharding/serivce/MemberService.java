package com.sjdbc.demo.commonsharding.serivce;

import com.sjdbc.demo.commonsharding.po.Member;

import java.util.List;

/**
 * @Descirption
 * @Author lx
 * @Date 2021/11/12
 */
public interface MemberService {
    void add(Member member);
    List<Member> list();
}
