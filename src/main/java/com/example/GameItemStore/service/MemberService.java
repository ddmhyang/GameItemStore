package com.example.GameItemStore.service;

import com.example.GameItemStore.entity.Member;
import com.example.GameItemStore.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    // 회원가입
    public void join(Member member) {
        member.setRole("USER"); // 기본 권한
        member.setMileage(0);   // 초기 마일리지
        memberRepository.save(member);
    }

    // 로그인
    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}