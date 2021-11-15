package com.sjdbc.demo.commonsharding.serivce.impl;

import com.sjdbc.demo.commonsharding.mapper.member.MemberMapper;
import com.sjdbc.demo.commonsharding.po.Member;
import com.sjdbc.demo.commonsharding.serivce.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Descirption
 * @Author lx
 * @Date 2021/11/12
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public void add(Member member) {
        memberMapper.add(member);
    }

    @Override
    public List<Member> list() {
        return memberMapper.list();
    }
}
