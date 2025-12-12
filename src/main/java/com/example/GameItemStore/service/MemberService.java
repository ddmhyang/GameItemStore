package com.example.GameItemStore.service;

import com.example.GameItemStore.entity.Member;
import com.example.GameItemStore.repository.MemberRepository;
import com.example.GameItemStore.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public void join(Member member) {
        member.setRole("USER");
        member.setMileage(100);
        memberRepository.save(member);
    }

    public Member login(String loginId, String password) {
        Member member = memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);

        if (member != null) {
            String token = jwtUtil.generateToken(member.getId(), member.getNickname());
            member.setToken(token);
        }

        return member;
    }

    public Member withdraw(Long memberId, int amount) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member != null) {
            if (member.getMileage() < amount) {
                throw new IllegalStateException("잔액이 부족합니다!");
            }
            member.setMileage(member.getMileage() - amount);
            memberRepository.save(member);
        }
        return member;
    }
}