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
        // [수정] 가입 축하금 100원 지급!
        member.setMileage(100);
        memberRepository.save(member);
    }

    // 로그인
    public Member login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
    public Member withdraw(Long memberId, int amount) {
        Member member = memberRepository.findById(memberId).orElse(null);
        if (member != null) {
            // 돈이 부족하면 에러!
            if (member.getMileage() < amount) {
                throw new IllegalStateException("잔액이 부족합니다!");
            }
            member.setMileage(member.getMileage() - amount); // 차감
            memberRepository.save(member);
        }
        return member; // 줄어든 잔액 정보를 다시 돌려줌
    }
}