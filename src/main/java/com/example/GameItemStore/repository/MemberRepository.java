package com.example.GameItemStore.repository;

import com.example.GameItemStore.entity.Member; // 올바른 패키지 import
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 로그인용: 아이디로 회원 찾기
    Optional<Member> findByLoginId(String loginId);
}