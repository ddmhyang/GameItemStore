package com.example.GameItemStore.service;

import com.example.GameItemStore.entity.Member;
import com.example.GameItemStore.repository.MemberRepository;
import com.example.GameItemStore.util.JwtUtil; // [추가]
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtUtil jwtUtil; // [추가] 토큰 기계 주입

    // ... join 메서드는 그대로 ...
    public void join(Member member) {
        member.setRole("USER");
        member.setMileage(100);
        memberRepository.save(member);
    }

    // [수정] 로그인 성공 시 토큰 생성해서 넣어줌!
    public Member login(String loginId, String password) {
        Member member = memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);

        if (member != null) {
            // 토큰 발행!
            String token = jwtUtil.generateToken(member.getId(), member.getNickname());
            member.setToken(token); // Member 객체에 토큰을 담아서 보냄
        }

        return member;
    }

    // ... withdraw 등 나머지 메서드는 그대로 ...
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