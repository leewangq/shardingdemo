package com.sjdbc.demo.commonsharding.controller;

import com.sjdbc.demo.commonsharding.po.Member;
import com.sjdbc.demo.commonsharding.serivce.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Descirption
 * @Author lx
 * @Date 2021/11/12
 */
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping("/add")
    public String add(){
        Member member=new Member();
        member.setName("jacky");
        member.setPhone("1883432234234");
        member.setCreateTime(new Date());
        memberService.add(member);
        return "ok";
    }
}
