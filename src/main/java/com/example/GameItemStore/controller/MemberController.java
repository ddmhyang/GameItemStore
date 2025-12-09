package com.example.GameItemStore.controller;

import com.example.GameItemStore.entity.Member;
import com.example.GameItemStore.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // 리액트 연동 허용
@RequestMapping("/api/members") // 공통 주소
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 회원가입 (POST /api/members/join)
    @PostMapping("/join")
    public String join(@RequestBody Member member) {
        memberService.join(member);
        return "회원가입 성공!";
    }

    // 로그인 (POST /api/members/login)
    @PostMapping("/login")
    public Member login(@RequestBody Member member) {
        return memberService.login(member.getLoginId(), member.getPassword());
    }
    @PostMapping("/{id}/withdraw")
    public Member withdraw(@PathVariable Long id, @RequestParam int amount) {
        return memberService.withdraw(id, amount);
    }
}