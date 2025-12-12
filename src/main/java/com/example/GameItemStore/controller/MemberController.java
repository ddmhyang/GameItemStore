package com.example.GameItemStore.controller;

import com.example.GameItemStore.entity.Member;
import com.example.GameItemStore.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/join")
    public String join(@RequestBody Member member) {
        memberService.join(member);
        return "회원가입 성공!";
    }

    @PostMapping("/login")
    public Member login(@RequestBody Member member) {
        return memberService.login(member.getLoginId(), member.getPassword());
    }
    @PostMapping("/{id}/withdraw")
    public Member withdraw(@PathVariable Long id, @RequestParam int amount) {
        return memberService.withdraw(id, amount);
    }

    @Autowired
    private com.example.GameItemStore.repository.MemberRepository memberRepository;

    @GetMapping
    public java.util.List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public String deleteMember(@PathVariable Long id) {
        memberRepository.deleteById(id);
        return "회원 삭제 완료";
    }
}